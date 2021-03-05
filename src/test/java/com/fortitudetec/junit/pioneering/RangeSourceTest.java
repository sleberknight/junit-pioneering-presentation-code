package com.fortitudetec.junit.pioneering;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junitpioneer.jupiter.params.DoubleRangeSource;
import org.junitpioneer.jupiter.params.IntRangeSource;
import org.junitpioneer.jupiter.params.LongRangeSource;

@DisplayName("@XxxRangeSource")
class RangeSourceTest {

    @ParameterizedTest
    @IntRangeSource(from = 0, to = 10)
    void shouldGenerateIntegers(int value) {
        assertThat(value).isBetween(0, 9);

        failIfSeeUpperBound(value, 10);
    }

    @ParameterizedTest
    @LongRangeSource(from = -100_000, to = 100_000, step = 5_000)
    void shouldAllowChangingTheStep(long value) {
        assertThat(value).isBetween(-100_000L, 95_000L);

        failIfSeeUpperBound(value, 100_000);
    }

    private void failIfSeeUpperBound(long value, int upperBoundExclusive) {
        if (value == upperBoundExclusive) {
            fail("'to' should be exclusive");
        }
    }

    @ParameterizedTest
    @DoubleRangeSource(from = 0.0, to = 10.0, step = 0.5, closed = true)
    void shouldAllowClosedRanges(double value) {
        assertThat(value).isBetween(0.0, 10.0);
    }

}
