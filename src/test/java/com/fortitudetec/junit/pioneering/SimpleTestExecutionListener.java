package com.fortitudetec.junit.pioneering;

import org.junit.platform.engine.reporting.ReportEntry;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;

public class SimpleTestExecutionListener implements TestExecutionListener {

    @Override
    public void reportingEntryPublished(TestIdentifier testIdentifier, ReportEntry entry) {
        var entries = entry.getKeyValuePairs();

        var name = getClass().getSimpleName();
        System.out.printf("--- [%s: BEGIN] ---%n", name);

        var timestamp = entry.getTimestamp();

        System.out.println("testIdentifier: " + testIdentifier);
        System.out.println("timestamp: " + timestamp);
        System.out.println("entries: " + entries);

        System.out.printf("--- [%s: END] ---%n", name);
    }
}
