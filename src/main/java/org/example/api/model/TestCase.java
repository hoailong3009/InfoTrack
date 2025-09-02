package org.example.api.model;

import java.util.HashMap;
import java.util.Map;

public class TestCase {
    private String testName;
    private Map<String, String> requestPathValues;
    private Map<String, Object> expectedValues;

    public TestCase(String testName, Map<String, String> requestPathValues, Map<String, Object> expectedValues) {
        this.testName = testName;
        this.requestPathValues = requestPathValues;
        this.expectedValues = expectedValues;
    }

    public TestCase(String testName, Map<String, Object> expectedValues) {
        this.testName = testName;
        this.requestPathValues = new HashMap<>();
        this.expectedValues = expectedValues;
    }
    
    public String getTestName() {
        return testName;
    }
    
    public Map<String, String> getRequestPathValues() {
        return requestPathValues;
    }
    
    public Map<String, Object> getExpectedValues() {
        return expectedValues;
    }
}
