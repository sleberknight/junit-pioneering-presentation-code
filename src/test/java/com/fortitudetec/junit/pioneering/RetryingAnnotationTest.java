package com.fortitudetec.junit.pioneering;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junitpioneer.jupiter.RetryingTest;

@DisplayName("@RetryingTest")
class RetryingAnnotationTest {

    static Flaky flaky;

    @BeforeAll
    static void beforeAll() {
        flaky = new Flaky();
    }

    @Disabled("Comment this line out if you want to see this fail...")
    @RetryingTest(2)
    void shouldFail() {
        flaky.failsFirstTwoTimes();
    }

    @RetryingTest(3)
    void shouldPass() {
        flaky.failsFirstTwoTimes();
    }

    static class Flaky {

        private int timesCalled;

        void failsFirstTwoTimes() {
            ++timesCalled;

            if (timesCalled <= 2) {
                throw new RuntimeException("Flaky failure");
            }
        }
    }
}

