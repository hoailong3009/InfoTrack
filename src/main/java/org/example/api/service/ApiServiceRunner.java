package org.example.api.service;

import io.restassured.response.Response;
import org.example.api.model.TestCase;
import org.example.api.template.ApiTemplate;
import org.example.api.utils.TestObserver;

import java.util.Map;

public class ApiServiceRunner {
    private Response lastResponse;

    private void setLastResponse(Response response) {
        this.lastResponse = response;
    }

    public Response executeApi(ApiTemplate apiTemplate, Map<String, String> requestData) {
        Response response = apiTemplate.doExecute(
                requestData != null && !requestData.isEmpty() 
                    ? ApiTemplate.createDataTableFromMap(requestData) 
                    : null);
        
        setLastResponse(response);
        return response;
    }
    
    public String verifyResponse(Map<String, Object> expectedValues) {
        StringBuilder failures = new StringBuilder();
        
        for (Map.Entry<String, Object> entry : expectedValues.entrySet()) {
            String fieldName = entry.getKey();
            Object expectedValue = entry.getValue();
            
            Object actualValue = getActualValue(fieldName);
            
            if (!isValueMatch(expectedValue, actualValue)) {
                failures.append(formatFailureMessage(fieldName, expectedValue, actualValue));
            }
        }
        
        return failures.toString();
    }


    public void executeAndVerifyResponseFromTemplate(String templatePath, TestCase[] testCases) {
        ApiTemplate apiTemplate = new ApiTemplate(templatePath);
        
        for (int i = 0; i < testCases.length; i++) {
            TestCase testCase = testCases[i];
            System.out.println("\nExecute test case #" + (i+1) + (testCase.getTestName() != null ? ": " + testCase.getTestName() : ""));
            
            executeApi(apiTemplate, testCase.getRequestPathValues());
            String verificationDetails = verifyResponse(testCase.getExpectedValues());
            boolean testCasePassed = !verificationDetails.contains("FAILED");
            
            String message = testCase.getTestName() + ": " + (testCasePassed ? "PASSED" : "FAILED") + "\n" + verificationDetails;
            TestObserver.getInstance().recordTestResult(testCasePassed, message);
        }
    }
    
    private Object getActualValue(String fieldName) {
        if ("statusCode".equals(fieldName)) {
            return lastResponse.getStatusCode();
        } else {
            String jsonPath = fieldName.startsWith("body.") ? fieldName.substring(5) : fieldName;
            return lastResponse.jsonPath().get(jsonPath);
        }
    }
    
    private boolean isValueMatch(Object expected, Object actual) {
        return expected == null ? actual == null : expected.equals(actual);
    }
    
    private String formatFailureMessage(String fieldName, Object expected, Object actual) {
        return fieldName + ": FAILED (Expected: " + expected + ", Actual: " + actual + ")\n";
    }

}