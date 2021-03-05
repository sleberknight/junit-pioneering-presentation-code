package com.fortitudetec.junit.pioneering;

import static org.assertj.core.api.Assertions.fail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junitpioneer.jupiter.params.DisableIfDisplayName;
import org.junitpioneer.jupiter.params.IntRangeSource;
import org.kiwiproject.test.junit.jupiter.params.provider.RandomIntSource;

import java.util.Arrays;
import java.util.regex.Pattern;

@DisplayName("@DisableIfDisplayName")
class DisableBasedOnDisplayNameTest {

    private static final Pattern PRODUCT_NUMBER_PATTERN = Pattern.compile(".*[0-9][3|5]");

    @DisableIfDisplayName(contains = "42")
    @ParameterizedTest(name = "Test scenario {0}")
    @ValueSource(ints = {1, 24, 42, 84, 168, 420, 4200, 4242, 17640})
    void shouldDisableUsingStringContains(int value) {
        if (String.valueOf(value).contains("42")) {
            fail("Should not have received %s", value);
        }
    }

    @DisableIfDisplayName(contains = {"42", "84"})
    @ParameterizedTest(name = "Test scenario {0}")
    @ValueSource(ints = {1, 24, 42, 84, 168})
    void shouldDisableUsingMoreThanOneStringContains(int value) {
        failIfContainsAny(value, 42, 84);
    }

    @DisableIfDisplayName(matches = ".*\\d{3}-\\d{2}-\\d{4}.*")
    @ParameterizedTest(name = "Test scenario {0}")
    @ValueSource(strings = {"42", "123-45-6789", "400", "234-56-7890", "888"})
    void shouldDisableUsingRegex(String value) {
        failIfContainsAny(value, "123-45-6789", "234-56-7890");
    }

    @DisableIfDisplayName(contains = {"42", "84"}, matches = ".*\\d{3}-\\d{2}-\\d{4}.*")
    @ParameterizedTest(name = "Test scenario {0}")
    @ValueSource(strings = {"24", "42", "123-45-6789", "400", "234-56-7890", "888"})
    void shouldDisableUsingContainsAndMatches(String value) {
        failIfContainsAny(value, "42", "84", "123-45-6789", "234-56-7890");
    }

    @DisableIfDisplayName(matches = ".*2$")
    @ParameterizedTest(name = "Disable if ends with two in number {0}")
    @IntRangeSource(from = 0, to = 100)
    void shouldDisableWhenEndsWithNumeralTwo(int value) {
        if (String.valueOf(value).endsWith("2")) {
            fail("Should not have received %d", value);
        }
    }

    @DisableIfDisplayName(matches = ".*[0-9][3|5]")
    @ParameterizedTest(name = "Product: FTCH-000-{0}")
    @RandomIntSource(min = 0, max = 1_000, count = 500)
    void shouldDisableWhenProductCodeEndsWith_X3_Or_X5(int code) {
        if (PRODUCT_NUMBER_PATTERN.matcher(String.valueOf(code)).matches()) {
            fail("Should not have received %d", code);
        }
    }

    private static void failIfContainsAny(int value, int... failValues) {
        var strings = Arrays.stream(failValues)
                .mapToObj(String::valueOf)
                .toArray(String[]::new);

        failIfContainsAny(String.valueOf(value), strings);
    }

    private static void failIfContainsAny(String value, String... failValues) {
        Arrays.stream(failValues)
                .filter(s -> s.equals(value))
                .findAny()
                .ifPresent(s -> fail("Should not have received %s", s));
    }
}
