package com.fortitudetec.junit.pioneering;

import static org.apache.commons.lang3.StringUtils.equalsAny;
import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.Range;
import org.junit.jupiter.api.DisplayName;
import org.junitpioneer.jupiter.cartesian.ArgumentSets;
import org.junitpioneer.jupiter.cartesian.CartesianTest;
import org.junitpioneer.jupiter.cartesian.CartesianTest.Enum.Mode;
import org.junitpioneer.jupiter.cartesian.CartesianTest.Values;
import org.junitpioneer.jupiter.params.IntRangeSource;
import org.junitpioneer.jupiter.params.LongRangeSource;

import java.util.List;
import java.util.stream.Stream;

@DisplayName("@CartesianTest")
public class CartesianTestAnnotationTest {

    // 4 inputs (2 to 2nd power)
    @CartesianTest
    void shouldProduceAllCombinationsOfTwo(@Values(strings = {"0", "1"}) String x,
                                           @Values(strings = {"0", "1"}) String y) {
        List.of(x, y).forEach(this::assertIsZeroOrOne);
    }

    // 8 inputs (2 to 3rd power)
    @CartesianTest
    void shouldProduceAllCombinationsOfThree(@Values(strings = {"0", "1"}) String x,
                                             @Values(strings = {"0", "1"}) String y,
                                             @Values(strings = {"0", "1"}) String z) {
        List.of(x, y, z).forEach(this::assertIsZeroOrOne);
    }

    private void assertIsZeroOrOne(String s) {
        assertThat(equalsAny(s, "0", "1"))
                .describedAs("Each argument should be '0' or '1'")
                .isTrue();
    }

    // 27 inputs (3 to 3rd power)
    @CartesianTest
    void shouldProduceAllCombinationsOfThree_ForThreeInputs(
            @Values(strings = {"A", "B", "C"}) String x,
            @Values(strings = {"A", "B", "C"}) String y,
            @Values(strings = {"A", "B", "C"}) String z) {
        List.of(x, y, z).forEach(this::assertIsABC);
    }

    // 81 inputs (3 to fourth power)
    @CartesianTest
    void shouldProduceAllCombinationsOfThree_ForFourInputs(
            @Values(strings = {"A", "B", "C"}) String w,
            @Values(strings = {"A", "B", "C"}) String x,
            @Values(strings = {"A", "B", "C"}) String y,
            @Values(strings = {"A", "B", "C"}) String z) {
        List.of(w, x, y, z).forEach(this::assertIsABC);
    }

    private void assertIsABC(String s) {
        assertThat(equalsAny(s, "A", "B", "C"))
                .describedAs("Each argument should be 'A' or 'B' or 'C'")
                .isTrue();
    }

    enum Result {
        SUCCEEDED, FAILED, SKIPPED
    }

    @CartesianTest
    void shouldProduceAllCombinationsForCartesianValueSources(
            @Values(strings = {"A", "B", "C"}) String x,
            @Values(ints = {1, 2, 3}) int y,
            @CartesianTest.Enum(
                    value = Result.class, names = {"SKIPPED"}, mode = Mode.EXCLUDE) Result z) {
        assertThat(equalsAny(x, "A", "B", "C")).isTrue();
        assertThat(Range.closed(1, 3).contains(y)).isTrue();
        assertThat(z).isNotNull();
    }

    @CartesianTest
    void shouldWorkWithRangeSources(@IntRangeSource(from = 1, to = 4, closed = true) int x,
                                    @LongRangeSource(from = 1000, to = 1005, closed = true) long y) {
        assertThat(Range.closed(1, 4).contains(x)).isTrue();
        assertThat(Range.closed(1000L, 1005L).contains(y)).isTrue();
    }

    @CartesianTest
    @CartesianTest.MethodFactory("nFold")
    void nFold(String greek, Result result, int level) {
        assertThat(equalsAny(greek, "Alpha", "Beta", "Gamma")).isTrue();
        assertThat(result).isNotNull();
        assertThat(Range.closedOpen(0, 5).contains(level)).isTrue();
    }

    // This *is* used by the above nFold test annotated with @CartesianTest.MethodFactory
    // even though IntelliJ thinks it isn't.
    @SuppressWarnings("unused")
    static ArgumentSets nFold() {
        return ArgumentSets
                .argumentsForFirstParameter("Alpha", "Beta", "Gamma")
                .argumentsForNextParameter((Object[]) Result.values())
                .argumentsForNextParameter(Stream.iterate(0, val -> val + 1).limit(5));
    }

    @CartesianTest
    @CartesianTest.MethodFactory("customProduct")
    void shouldAllowCustomArgumentFactory(String greek, Result result, int level) {
        assertThat(equalsAny(greek, "Alpha", "Beta", "Gamma")).isTrue();
        assertThat(result).isNotNull();
        assertThat(Range.closedOpen(0, 5).contains(level)).isTrue();
    }

    // This *is* used by the above test annotated with @CartesianTest specifying this
    // method as the factory.
    @SuppressWarnings("unused")
    static ArgumentSets customProduct() {
        return ArgumentSets
                .argumentsForFirstParameter("Alpha", "Beta", "Gamma")
                .argumentsForNextParameter((Object[]) Result.values())
                .argumentsForNextParameter(Stream.iterate(0, val -> val + 1).limit(5));
    }
}
