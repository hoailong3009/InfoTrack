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
        if (lastResponse == null) {
            throw new IllegalStateException("No response to validate. Please call the API first.");
        }

        StringBuilder resultDetails = new StringBuilder();

        for (Map.Entry<String, Object> entry : expectedValues.entrySet()) {
            String key = entry.getKey();
            Object expectedValue = entry.getValue();
            
            boolean verificationPassed;
            if ("statusCode".equals(key)) {
                verificationPassed = lastResponse.getStatusCode() == (int) expectedValue;
            } else {
                String jsonPath = key.startsWith("body.") ? key.substring(5) : key;
                Object actualValue = lastResponse.jsonPath().get(jsonPath);
                verificationPassed = expectedValue == null ? actualValue == null : expectedValue.equals(actualValue);
            }
            
            String detail = key + ": " + (verificationPassed ? "Passed" : "Failed");
            resultDetails.append(detail).append("\n");
        }

        return resultDetails.toString();
    }

    public void executeAndVerifyResponseFromTemplate(String templatePath, TestCase[] testCases) {
        ApiTemplate apiTemplate = new ApiTemplate(templatePath);        
        String suiteName = "API Test Suite - " + templatePath.substring(templatePath.lastIndexOf('/') + 1);
        TestObserver.getInstance().startTestSuite(suiteName);
        
        for (int i = 0; i < testCases.length; i++) {
            TestCase testCase = testCases[i];
            System.out.println("\nExecute test case #" + (i+1) + (testCase.getTestName() != null ? ": " + testCase.getTestName() : ""));
            
            TestObserver.getInstance().startTestCase(testCase);
            
            executeApi(apiTemplate, testCase.getRequestPathValues());

            String errorDetails = verifyResponse(testCase.getExpectedValues());
            boolean testCasePassed = !errorDetails.contains("Failed");
            
            TestObserver.getInstance().recordTestResult(testCasePassed,
                testCase.getTestName() + ": " + (testCasePassed ? "PASSED" : "FAILED"));
            
        }
        TestObserver.getInstance().endTestSuite();
    }

}