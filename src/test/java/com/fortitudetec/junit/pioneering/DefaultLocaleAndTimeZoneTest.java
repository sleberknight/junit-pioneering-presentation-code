package com.fortitudetec.junit.pioneering;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junitpioneer.jupiter.DefaultLocale;
import org.junitpioneer.jupiter.DefaultTimeZone;

import java.util.Locale;
import java.util.TimeZone;

import lombok.extern.slf4j.Slf4j;

@DisplayName("@DefaultLocale and @DefaultTimeZone")
@DefaultLocale("es")
@DefaultTimeZone("America/Mexico_City")
@Slf4j
class DefaultLocaleAndTimeZoneTest {

    @BeforeEach
    void setUp(TestInfo testInfo) {
        LOG.info("{}:", testInfo.getDisplayName());
        LOG.info("\tDefault Locale: {} ; default TimeZone: {} ({})",
                Locale.getDefault(),
                TimeZone.getDefault().getID(),
                TimeZone.getDefault().getDisplayName());
    }

    @Test
    void shouldSetDefaultLocaleFromClassLevel() {
        assertThat(Locale.getDefault()).isEqualTo(new Locale.Builder().setLanguage("es").build());
    }

    @Nested
    class ShouldChangeDefaultLocale {

        // Latin American Spanish
        @Test
        @DefaultLocale("es-419")
        void usingIETF_BCP_47_LanguageTag() {
            assertThat(Locale.getDefault()).isEqualTo(Locale.forLanguageTag("es-419"));
        }

        // Spanish
        @Test
        @DefaultLocale(language = "es")
        void usingLanguage() {
            assertThat(Locale.getDefault()).isEqualTo(Locale.forLanguageTag("es"));
        }

        // Spanish - Mexico
        @Test
        @DefaultLocale(language = "es", country = "MX")
        void usingLanguageAndCountry() {
            assertThat(Locale.getDefault()).isEqualTo(
                    new Locale.Builder().setLanguage("es").setRegion("MX").build());
        }

        // see: https://en.wikipedia.org/wiki/IETF_language_tag
        @Test
        @DefaultLocale(language = "ja", country = "JP", variant = "japanese")
        void usingLanguageAndCountryAndVariant() {
            assertThat(Locale.getDefault()).isEqualTo(
                new Locale.Builder().setLanguage("ja").setRegion("JP").setVariant("japanese").build());
        }
    }

    @Test
    void shouldSetDefaultTimeZoneFromClassLevel() {
        assertThat(TimeZone.getDefault()).isEqualTo(TimeZone.getTimeZone("America/Mexico_City"));
    }

    @Test
    @DefaultTimeZone("Mexico/BajaSur")
    void shouldPreferDefaultTimeZoneFromMethodLevel() {
        assertThat(TimeZone.getDefault()).isEqualTo(TimeZone.getTimeZone("Mexico/BajaSur"));
    }
}
