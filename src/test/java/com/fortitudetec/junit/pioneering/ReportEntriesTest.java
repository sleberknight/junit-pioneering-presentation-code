package com.fortitudetec.junit.pioneering;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.ReportEntry;

@DisplayName("@ReportEntry")
class ReportEntriesTest {

    @Test
    @ReportEntry("Reporting for duty as ordered!")
    void shouldPublishAReportEntry() {
        assertThat(4 + 2).isEqualTo(6);
    }

    @Test
    @ReportEntry("This is my initial report")
    @ReportEntry("Here's an update!")
    void shouldPublishMultipleReportEntries() {
        assertThat(42 / 2).isEqualTo(21);
    }

    @Test
    @ReportEntry(key = "name", value = "shouldPublishConditionally")
    @ReportEntry(key = "result", value = "success", when = ReportEntry.PublishCondition.ON_SUCCESS)
    @ReportEntry(key = "result", value = "failed", when = ReportEntry.PublishCondition.ON_FAILURE)
    @ReportEntry(key = "result", value = "aborted", when = ReportEntry.PublishCondition.ON_ABORTED)
    void shouldPublishConditionally() {
        assertThat(21 * 2).isEqualTo(42);
    }
}
