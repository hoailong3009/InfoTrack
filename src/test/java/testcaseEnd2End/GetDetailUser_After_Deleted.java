package testcaseEnd2End;

import org.example.api.model.TestCase;
import org.example.api.pages.ApiPage;
import org.example.api.utils.ApiTestData;
import org.example.api.utils.ConfigManager;

public class GetDetailUser_After_Deleted {

    public static void main(String[] args) {
        ConfigManager.initializeConfig();
        String baseUrl = ConfigManager.getBaseUrl();
        ApiPage apiPage = new ApiPage();

        TestCase[] deletedUser = new TestCase[] {
                new TestCase(
                        "Delete success userID = 5",
                        ApiTestData.createRequest("url", baseUrl + "/api/users/5"),
                        ApiTestData.expectedResponseOf(
                                "statusCode", 204))
        };
        apiPage.executeAndVerifyResponseFromTemplate(System.getProperty("user.dir") +"/src/data/DeletedUser.json",deletedUser);


        TestCase[] getDetailUser = new TestCase[] {
                new TestCase(
                        "Get userID = 5 not found",
                        ApiTestData.createRequest("url", baseUrl + "/api/users/5"),
                        ApiTestData.expectedResponseOf(
                                "statusCode", 404))
        };
        apiPage.executeAndVerifyResponseFromTemplate(System.getProperty("user.dir") +"/src/data/GetDetailUser.json", getDetailUser);
    }
}