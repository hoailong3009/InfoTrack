package testcase4Endpoint;

import org.example.api.model.TestCase;
import org.example.api.pages.ApiPage;
import org.example.api.utils.ApiTestData;

public class API_CreateUser {

    public static void main(String[] args) {
        String jsonFile = "/src/data/CreateUser.json";
        ApiPage apiPage = new ApiPage();
        TestCase[] createUser = new TestCase[] {
                new TestCase("Create success new user without update data from API", ApiTestData.expectedResponseOf(
                        "statusCode", 201
                        )
                ) ,
                new TestCase(
                        "Create success new user with firstName & lastName",
                        ApiTestData.createRequest("data.first_name", "Kevin",
                                                                "data.last_name", "Cle",
                                                                "data.email", "kevin@gmail.com"),
                        ApiTestData.expectedResponseOf("statusCode", 201)
                ),
                new TestCase(
                        "Create failed new user with miss required field",
                        ApiTestData.createRequest("data.first_name", "",
                                                                "data.last_name", ""),
                        ApiTestData.expectedResponseOf("statusCode", 400)
                ),
                new TestCase(
                        "Create failed new user with exist email",
                        ApiTestData.createRequest("data.email","kevin@gmail.com",
                                                                "data.first_name", "Long",
                                                                "data.last_name", "Quach"),
                        ApiTestData.expectedResponseOf("statusCode", 400)
                )
        };
        apiPage.executeAndVerifyResponseFromTemplate(System.getProperty("user.dir") +jsonFile, createUser);
        
    }

}