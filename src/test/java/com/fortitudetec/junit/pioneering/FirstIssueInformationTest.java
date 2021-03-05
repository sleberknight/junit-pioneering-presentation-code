package com.fortitudetec.junit.pioneering;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.Issue;

@DisplayName("Issue Information 1")
class FirstIssueInformationTest {

    @Test
    @Issue("JUPI-1234")
    void shouldFix1234() {
        assertThat(1 + 1).isEqualTo(2);
    }

    @Test
    @Issue("JUPI-2345")
    void shouldResolve2345() {
        assertThat(2 + 3).isEqualTo(5);
    }

    @Test
    @Issue("JUPI-3456")
    void shouldBeFor3456() {
        assertThat(4 / 2).isEqualTo(2);
    }

    @Test
    @Issue("JUPI-3456")
    void shouldAlsoBeFor3456() {
        assertThat(4 / 2).isEqualTo(2);
    }
}
