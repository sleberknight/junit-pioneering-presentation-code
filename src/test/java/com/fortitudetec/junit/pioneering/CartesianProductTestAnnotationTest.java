package com.fortitudetec.junit.pioneering;

import static org.apache.commons.lang3.StringUtils.equalsAny;
import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.Range;
import org.junit.jupiter.api.DisplayName;
import org.junitpioneer.jupiter.CartesianEnumSource;
import org.junitpioneer.jupiter.CartesianProductTest;
import org.junitpioneer.jupiter.CartesianValueSource;
import org.junitpioneer.jupiter.params.IntRangeSource;
import org.junitpioneer.jupiter.params.LongRangeSource;

import java.util.List;
import java.util.stream.Stream;

@DisplayName("@CartesianProductTest")
public class CartesianProductTestAnnotationTest {

    // 4 inputs (2 to 2nd power)
    @CartesianProductTest({"0", "1"})
    void shouldProduceAllCombinationsOfTwo(String x, String y) {
        List.of(x, y).forEach(this::assertIsZeroOrOne);
    }

    // 8 inputs (2 to 3rd power)
    @CartesianProductTest({"0", "1"})
    void shouldProduceAllCombinationsOfThree(String x, String y, String z) {
        List.of(x, y, z).forEach(this::assertIsZeroOrOne);
    }

    private void assertIsZeroOrOne(String s) {
        assertThat(equalsAny(s, "0", "1"))
                .describedAs("Each argument should be '0' or '1'")
                .isTrue();
    }

    // 27 inputs (3 to 3rd power)
    @CartesianProductTest({"A", "B", "C"})
    void shouldProduceAllCombinationsOfThree_ForThreeInputs(String x, String y, String z) {
        List.of(x, y, z).forEach(this::assertIsABC);
    }

    // 81 inputs (3 to fourth power)
    @CartesianProductTest({"A", "B", "C"})
    void shouldProduceAllCombinationsOfThree_ForFourInputs(String w, String x, String y, String z) {
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

    @CartesianProductTest
    @CartesianValueSource(strings = {"A", "B", "C"})
    @CartesianValueSource(ints = {1, 2, 3})
    @CartesianEnumSource(value = Result.class,
            names = {"SKIPPED"},
            mode = CartesianEnumSource.Mode.EXCLUDE)
    void shouldProduceAllCombinationsForCartesianValueSources(String x, int y, Result z) {
        assertThat(equalsAny(x, "A", "B", "C")).isTrue();
        assertThat(Range.closed(1, 3).contains(y)).isTrue();
        assertThat(z).isNotNull();
    }

    @CartesianProductTest
    @IntRangeSource(from = 1, to = 4, closed = true)
    @LongRangeSource(from = 1000, to = 1005, closed = true)
    void shouldWorkWithRangeSources(int x, long y) {
        assertThat(Range.closed(1, 4).contains(x)).isTrue();
        assertThat(Range.closed(1000L, 1005L).contains(y)).isTrue();
    }

    @CartesianProductTest
    void nFold(String greek, Result result, int level) {
        assertThat(equalsAny(greek, "Alpha", "Beta", "Gamma")).isTrue();
        assertThat(result).isNotNull();
        assertThat(Range.closedOpen(0, 5).contains(level)).isTrue();
    }

    // This *is* used by the above nFold test annotated with @CartesianProductTest
    // even though IntelliJ thinks it isn't.
    @SuppressWarnings("unused")
    static CartesianProductTest.Sets nFold() {
        return new CartesianProductTest.Sets()
                .add("Alpha", "Beta", "Gamma")
                .add((Object[]) Result.values())
                .addAll(Stream.iterate(0, val -> val + 1).limit(5));
    }

    @CartesianProductTest(factory = "customProduct")
    void shouldAllowCustomArgumentFactory(String greek, Result result, int level) {
        assertThat(equalsAny(greek, "Alpha", "Beta", "Gamma")).isTrue();
        assertThat(result).isNotNull();
        assertThat(Range.closedOpen(0, 5).contains(level)).isTrue();
    }

    // This *is* used by the above test annotated with @CartesianProductTest specifying this
    // method as the factory.
    @SuppressWarnings("unused")
    static CartesianProductTest.Sets customProduct() {
        return new CartesianProductTest.Sets()
                .add("Alpha", "Beta", "Gamma")
                .add((Object[]) Result.values())
                .addAll(Stream.iterate(0, val -> val + 1).limit(5));
    }
}
