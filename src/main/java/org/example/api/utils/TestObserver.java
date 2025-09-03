package org.example.api.utils;

import org.example.api.model.TestCase;

public class TestObserver {

    private static TestObserver instance;
    private static boolean testSuiteStarted = false;
    private static int testCaseCount = 0;

    public static synchronized TestObserver getInstance() {
        if (instance == null) {
            instance = new TestObserver();
        }
        return instance;
    }

    private TestObserver() {
        ExtentReporter.initReport();
    }

    public void recordTestResult(boolean isPassed, String message) {
        // Auto start suite if not started
        if (!testSuiteStarted) {
            autoStartTestSuite();
        }

        testCaseCount++;

        // Format message with HTML line breaks for better readability in report
        String formattedMessage = message.replace("\n", "<br/>");

        if (isPassed) {
            ExtentReporter.pass(formattedMessage);
        } else {
            ExtentReporter.error(formattedMessage);
        }
    }

    private void autoStartTestSuite() {
        String suiteName = "API Test Suite";
        ExtentReporter.startTest(suiteName, "");
        testSuiteStarted = true;
        testCaseCount = 0;
    }

    // Optional method to finalize report (can be called at end of test run)
    public static void finalizeReport() {
        if (testSuiteStarted) {
            testSuiteStarted = false;
            testCaseCount = 0;
        }
    }
}
