package testLogin_Success_UnSuccess;

import org.example.api.model.TestCase;
import org.example.api.pages.ApiPage;
import org.example.api.utils.ApiTestData;
import org.example.api.utils.ConfigManager;

public class Login_Success_UnSuccess {

    public static void main(String[] args) {
        ConfigManager.initializeConfig();
        
        ApiPage apiPage = new ApiPage();

        TestCase[] loginUserTestCases = new TestCase[] {
                new TestCase(
                        "Login Success",
                        ApiTestData.expectedResponseOf(
                                "statusCode", 200)),
                new TestCase(
                        "Login UnSuccess",
                        ApiTestData.createRequest("data.password", ""),
                        ApiTestData.expectedResponseOf(
                                "statusCode", 200))
        };
        
        apiPage.executeAndVerifyResponseFromTemplate(System.getProperty("user.dir") + "/src/data/Login.json", loginUserTestCases);
    }
}