package com.fortitudetec.junit.pioneering;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.ClearEnvironmentVariable;
import org.junitpioneer.jupiter.SetEnvironmentVariable;

@DisplayName("Environment Variable Annotations")
@SetEnvironmentVariable(key = "CLASS_VAR_1", value = "value1")
@SetEnvironmentVariable(key = "CLASS_VAR_2", value = "value2")
class EnvironmentVariablesTest {

    @BeforeAll
    static void beforeAll() {
        var value = System.getenv("EXTERNAL");
        if (isBlank(value)) {
            System.out.println("WARNING: No environment variable 'EXTERNAL' exists; the" +
                    " 'shouldClearExternallySetEnvironmentVariables' test won't correctly demonstrate clearing environment variables!");
        }
    }

    @Test
    void shouldReadEnvironmentVariablesSetAtClassLevel() {
        assertThat(System.getenv("CLASS_VAR_1")).isEqualTo("value1");
        assertThat(System.getenv("CLASS_VAR_2")).isEqualTo("value2");
    }

    // Assumes the test is launched an environment variable named EXTERNAL (having any value)
    @Test
    @ClearEnvironmentVariable(key = "EXTERNAL")
    void shouldClearExternallySetEnvironmentVariables() {
        assertThat(System.getenv("EXTERNAL")).isNull();
    }

    @Test
    @SetEnvironmentVariable(key = "METHOD_VAR_1", value = "A")
    @SetEnvironmentVariable(key = "METHOD_VAR_2", value = "B")
    void shouldSetEnvironmentVariablesAtMethodLevel() {
        assertThat(System.getenv("METHOD_VAR_1")).isEqualTo("A");
        assertThat(System.getenv("METHOD_VAR_2")).isEqualTo("B");
    }

    @Test
    @SetEnvironmentVariable(key = "CLASS_VAR_2", value = "override")
    void shouldPreferMethodLevelToClassLevel() {
        assertThat(System.getenv("CLASS_VAR_1")).isEqualTo("value1");
        assertThat(System.getenv("CLASS_VAR_2")).isEqualTo("override");
    }

    @Test
    @ClearEnvironmentVariable(key = "CLASS_VAR_1")
    @ClearEnvironmentVariable(key = "CLASS_VAR_2")
    @SetEnvironmentVariable(key = "METHOD_VAR_1", value = "TipTop")
    void shouldBeCombinable() {
        assertThat(System.getenv("CLASS_VAR_1")).isNull();
        assertThat(System.getenv("CLASS_VAR_2")).isNull();
        assertThat(System.getenv("METHOD_VAR_1")).isEqualTo("TipTop");
    }
}
