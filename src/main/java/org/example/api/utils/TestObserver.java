package org.example.api.utils;

import org.example.api.model.TestCase;

public class TestObserver {

    private static TestObserver instance;
    private static boolean testSuiteStarted = false;

    public static synchronized TestObserver getInstance() {
        if (instance == null) {
            instance = new TestObserver();
        }
        return instance;
    }
    
    private TestObserver() {
        ExtentReporter.initReport();
    }

    public void startTestSuite(String suiteName) {
        if (!testSuiteStarted) {
            ExtentReporter.startTest(suiteName, "Test Suite: " + suiteName);
            testSuiteStarted = true;
        }
    }

    public void startTestCase(TestCase testCase) {
        if (!testSuiteStarted) {
            startTestSuite("Default Test Suite");
        }
    }

    public void recordTestResult(boolean isPassed, String message) {
        if (isPassed) {
            ExtentReporter.pass(message);
        } else {
            ExtentReporter.error(message);
        }
    }
    
    public void endTestSuite() {
        if (testSuiteStarted) {
            testSuiteStarted = false;
        }
    }
}
