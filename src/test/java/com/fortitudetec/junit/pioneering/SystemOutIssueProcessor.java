package com.fortitudetec.junit.pioneering;

import static java.util.Comparator.comparing;

import org.junitpioneer.jupiter.IssueProcessor;
import org.junitpioneer.jupiter.IssueTestCase;
import org.junitpioneer.jupiter.IssueTestSuite;

import java.util.List;

public class SystemOutIssueProcessor implements IssueProcessor {

    @Override
    public void processTestResults(List<IssueTestSuite> issueTestSuites) {
        var name = SystemOutIssueProcessor.class.getSimpleName();
        System.out.printf("--- [%s: BEGIN] ---%n", name);
        System.out.printf("Reporting on %d issues%n%n", issueTestSuites.size());
        issueTestSuites.stream()
                .sorted(comparing(IssueTestSuite::issueId))
                .forEach(this::print);
        System.out.printf("--- [%s: END] ---%n", name);
    }

    private void print(IssueTestSuite suite) {
        System.out.printf("Issue %s:%n", suite.issueId());
        suite.tests().forEach(this::print);
        System.out.println();
    }

    private void print(IssueTestCase testCase) {
        System.out.printf("%s: %s%n", testCase.testId(), testCase.result());
    }
}
