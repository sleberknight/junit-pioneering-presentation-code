package com.fortitudetec.junit.pioneering;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.Issue;

@DisplayName("Issue Information 2")
class SecondIssueInformationTest {

    @Test
    @Issue("JUPI-6789")
    void shouldResolve6789() {
        assertThat(6 * 7).isEqualTo(42);
    }
}
