package com.fortitudetec.junit.pioneering;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junitpioneer.jupiter.Stopwatch;
import org.kiwiproject.base.DefaultEnvironment;
import org.kiwiproject.base.KiwiEnvironment;

import java.util.concurrent.ThreadLocalRandom;

@DisplayName("@Stopwatch")
class StopwatchTest {

    private static final KiwiEnvironment KIWI_ENVIRONMENT = new DefaultEnvironment();

    @RepeatedTest(value = 10)
    @Stopwatch
    void shouldReportElapsedTime() {
        var origin = 10L;
        var bound = 200L;
        var milliseconds = ThreadLocalRandom.current().nextLong(origin, bound);
        var interrupted = KIWI_ENVIRONMENT.sleepQuietly(milliseconds);

        assertThat(interrupted).isFalse();
        assertThat(milliseconds).isBetween(origin, bound);
    }
}
