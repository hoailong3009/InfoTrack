package testcase4Endpoint;

import org.example.api.model.TestCase;
import org.example.api.pages.ApiPage;
import org.example.api.utils.ApiTestData;
import org.example.api.utils.ConfigManager;

public class API_GetDetailUser {

    public static void main(String[] args) {
        ConfigManager.initializeConfig();
        
        String jsonFile = "/src/data/GetDetailUser.json";
        String baseUrl = ConfigManager.getBaseUrl();
        ApiPage apiPage = new ApiPage();
        TestCase[] getDetailUser = new TestCase[] {
                new TestCase("Get success user without update data form API", ApiTestData.expectedResponseOf(
                        "statusCode", 200,
                        "data.id", 2,
                        "data.email","janet.weaver@reqres.in",
                        "data.first_name", "Janet",
                        "data.last_name","Weaver",
                        "data.avatar","https://reqres.in/img/faces/2-image.jpg"
                )),
                new TestCase(
                        "Get success user with ID = 5",
                        ApiTestData.createRequest("url", baseUrl + "/api/users/5"),
                        ApiTestData.expectedResponseOf(
                                "statusCode", 200,
                                "data.id",5,
                                "data.email","charles.morris@reqres.in",
                                "data.first_name", "Charles",
                                "data.last_name","Morris",
                                "data.avatar","https://reqres.in/img/faces/5-image.jpg")
                ),
                new TestCase(
                        "Get failed user wrong url",
                        ApiTestData.createRequest("url", baseUrl + "/apiv/user/1"),
                        ApiTestData.expectedResponseOf("statusCode", 404)
                ),
                new TestCase(
                        "Get failed user not exist",ApiTestData.createRequest("url", baseUrl + "/api/user/abc123"),
                        ApiTestData.expectedResponseOf("statusCode", 404)
                )

        };
        apiPage.executeAndVerifyResponseFromTemplate(System.getProperty("user.dir") +jsonFile, getDetailUser);
    }

}
