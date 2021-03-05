package com.fortitudetec.junit.pioneering;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junitpioneer.jupiter.params.IntRangeSource;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * <strong>WARNING:</strong>
 * Storing the values seen in a {@link ParameterizedTest} in a static field a TERRIBLE HACK to verify the values
 * received, and is only done here for, um, illustrative purposes. It is NOT clever or a good thing to do.
 * <p>
 * Please, never do this in "real life".
 */
@DisplayName("@XxxRangeSource -- READ THE WARNING")
@SuppressWarnings({"java:S3415"})
class HackyRangeSourceTest {

    /**
     * See WARNING above!
     */
    private static final List<Integer> OPEN_RANGE_SEEN_VALUES = new ArrayList<>();

    /**
     * See WARNING above!
     */
    private static final List<Integer> OPEN_RANGE_CUSTOM_STEP_SEEN_VALUES = new ArrayList<>();

    /**
     * See WARNING above!
     */
    private static final List<Integer> CLOSED_RANGE_SEEN_VALUES = new ArrayList<>();

    /**
     * See WARNING above!
     */
    @ParameterizedTest
    @IntRangeSource(from = 0, to = 10)
    void shouldSeeValuesOfClosedRange(int value) {
        OPEN_RANGE_SEEN_VALUES.add(value);

        var expected = expected(value, val -> val + 1);

        assertThat(OPEN_RANGE_SEEN_VALUES).containsExactlyElementsOf(expected);
    }

    /**
     * See WARNING above!
     */
    @ParameterizedTest
    @IntRangeSource(from = 0, to = 20, step = 2)
    void shouldSeeValuesOfClosedRangeHavingCustomStep(int value) {
        OPEN_RANGE_CUSTOM_STEP_SEEN_VALUES.add(value);

        var expected = expected(value / 2, val -> val + 2);

        assertThat(OPEN_RANGE_CUSTOM_STEP_SEEN_VALUES).containsExactlyElementsOf(expected);
    }

    /**
     * See WARNING above!
     */
    @ParameterizedTest
    @IntRangeSource(from = 0, to = 10, closed = true)
    void shouldSeeValuesOfOpenRange(int value) {
        CLOSED_RANGE_SEEN_VALUES.add(value);

        var expected = expected(value, val -> val + 1);

        assertThat(CLOSED_RANGE_SEEN_VALUES).containsExactlyElementsOf(expected);
    }

    private static List<Integer> expected(int max, UnaryOperator<Integer> operation) {
        return Stream.iterate(0, operation)
                .limit(max + 1)
                .collect(toList());
    }
}
