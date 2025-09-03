package org.example.api.pages;

import org.example.api.model.TestCase;
import org.example.api.service.ApiServiceRunner;


public class ApiPage {
    
    private final ApiServiceRunner apiService;
    public ApiPage() {
        this.apiService = new ApiServiceRunner();
    }

    public void executeAndVerifyResponseFromTemplate(String templatePath, TestCase[] testCases) {
        apiService.executeAndVerifyResponseFromTemplate(templatePath, testCases);
    }
}