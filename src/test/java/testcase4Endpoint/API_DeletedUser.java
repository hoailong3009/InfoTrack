package testcase4Endpoint;

import org.example.api.model.TestCase;
import org.example.api.pages.ApiPage;
import org.example.api.utils.ApiTestData;
import org.example.api.utils.ConfigManager;

public class API_DeletedUser {

    public static void main(String[] args) {
        ConfigManager.initializeConfig();
        
        String jsonFile = "/src/data/DeletedUser.json";
        String baseUrl = ConfigManager.getBaseUrl();
        ApiPage apiPage = new ApiPage();
        TestCase[] deleteUser = new TestCase[] {
                new TestCase("Delete success user without update request API", ApiTestData.expectedResponseOf(
                        "statusCode", 204
                )),
                new TestCase(
                        "Delete success user ID = 5",
                        ApiTestData.createRequest("url", baseUrl + "/api/users/5"),
                        ApiTestData.expectedResponseOf("statusCode", 204)
                ),
                new TestCase(
                        "Delete failed user wrong url",
                        ApiTestData.createRequest("url", baseUrl + "/api/use/5zx"),
                        ApiTestData.expectedResponseOf("statusCode", 404)
                ),
                new TestCase(
                        "Delete failed user deleted",
                        ApiTestData.createRequest("url", baseUrl + "/api/users/5"),
                        ApiTestData.expectedResponseOf("statusCode", 404)
                )
        };
        apiPage.executeAndVerifyResponseFromTemplate(System.getProperty("user.dir") +jsonFile, deleteUser);
        
    }

}