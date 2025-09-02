package org.example.api.pages;

import org.example.api.model.TestCase;
import org.example.api.service.ApiServiceRunner;
import org.example.api.template.ApiTemplate;

import java.util.Map;


public class ApiPage {
    
    private final ApiServiceRunner apiService;
    public ApiPage() {
        this.apiService = new ApiServiceRunner();
    }

    public void executeAndVerifyResponseFromTemplate(String templatePath, TestCase[] testCases) {
        apiService.executeAndVerifyResponseFromTemplate(templatePath, testCases);
    }

    public void executeFromTemplateWithData(String templatePath, Map<String, String> requestData) {
        ApiTemplate apiTemplate = new ApiTemplate(templatePath);
        apiService.executeApi(apiTemplate, requestData);
    }

}