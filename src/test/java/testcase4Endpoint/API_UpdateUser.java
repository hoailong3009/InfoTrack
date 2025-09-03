package testcase4Endpoint;

import org.example.api.model.TestCase;
import org.example.api.pages.ApiPage;
import org.example.api.utils.ApiTestData;
import org.example.api.utils.ConfigManager;

public class API_UpdateUser {

    public static void main(String[] args) {
        ConfigManager.initializeConfig();
        
        String jsonFile = "/src/data/UpdateUser.json";
        String baseUrl = ConfigManager.getBaseUrl();
        ApiPage apiPage = new ApiPage();
        TestCase[] updateUser = new TestCase[] {
                new TestCase("Update success user without update data from API", ApiTestData.expectedResponseOf(
                        "statusCode", 200
                )),
                new TestCase(
                        "Update success user with firstName & lastName",
                        ApiTestData.createRequest("data.first_name", "Kevin",
                                                                "data.last_name", "Cle"),
                        ApiTestData.expectedResponseOf("statusCode", 200)
                ),
                new TestCase(
                        "Update failed user with wrong format firstName & lastName",
                        ApiTestData.createRequest("data.first_name", 1,
                                                                "data.last_name", 2),
                        ApiTestData.expectedResponseOf("statusCode", 400)
                ),
                new TestCase(
                        "Update failed user not exist",
                        ApiTestData.createRequest("url", baseUrl + "/api/users/5zx",
                                                                "data.last_name", "ABC"),
                        ApiTestData.expectedResponseOf("statusCode", 404)
                ),
                new TestCase(
                        "Update failed user deleted",
                        ApiTestData.createRequest("url", baseUrl + "/api/users/20",
                                                                "data.last_name", "ABC"),
                        ApiTestData.expectedResponseOf("statusCode", 404)
                ),


        };
        apiPage.executeAndVerifyResponseFromTemplate(System.getProperty("user.dir") + jsonFile, updateUser);
    }
}