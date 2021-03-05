package com.fortitudetec.junit.pioneering;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.ClearSystemProperty;
import org.junitpioneer.jupiter.SetSystemProperty;

@DisplayName("System Property Annotations")
@SetSystemProperty(key = "ClassProperty_1", value = "42")
@SetSystemProperty(key = "ClassProperty_2", value = "84")
class SystemPropertiesTest {

    @BeforeAll
    static void beforeAll() {
        var value = System.getProperty("ExistingProperty_1");
        if (isBlank(value)) {
            System.out.println("WARNING: No system property 'ExistingProperty_1' exists; the" +
                    " 'shouldClearExternallySetSystemProperties' test won't correctly demonstrate clearing system properties!");
        }
    }

    @Test
    void shouldReadSystemPropertiesSetAtClassLevel() {
        assertThat(System.getProperty("ClassProperty_1")).isEqualTo("42");
        assertThat(System.getProperty("ClassProperty_2")).isEqualTo("84");
    }

    // Assumes the test is launched with -DExistingProperty_1=some_value
    @Test
    @ClearSystemProperty(key = "ExistingProperty_1")
    void shouldClearExternallySetSystemProperties() {
        assertThat(System.getProperty("ExistingProperty_1")).isNull();
    }

    @Test
    @SetSystemProperty(key = "MethodProperty_1", value = "7")
    @SetSystemProperty(key = "MethodProperty_2", value = "8")
    void shouldSetSystemPropertiesAtMethodLevel() {
        assertThat(System.getProperty("MethodProperty_1")).isEqualTo("7");
        assertThat(System.getProperty("MethodProperty_2")).isEqualTo("8");
    }

    @Test
    @SetSystemProperty(key = "ClassProperty_1", value = "100")
    void shouldPreferMethodLevelToClassLevel() {
        assertThat(System.getProperty("ClassProperty_1")).isEqualTo("100");
        assertThat(System.getProperty("ClassProperty_2")).isEqualTo("84");
    }

    @Test
    @ClearSystemProperty(key = "ClassProperty_2")
    @SetSystemProperty(key = "Prop1", value = "v1")
    @SetSystemProperty(key = "Prop2", value = "v2")
    void shouldBeCombinable() {
        assertThat(System.getProperty("ClassProperty_1")).isEqualTo("42");
        assertThat(System.getProperty("ClassProperty_2")).isNull();
        assertThat(System.getProperty("Prop1")).isEqualTo("v1");
        assertThat(System.getProperty("Prop2")).isEqualTo("v2");
    }
}
