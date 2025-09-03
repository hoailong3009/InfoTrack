package testcaseEnd2End;

import org.example.api.model.TestCase;
import org.example.api.pages.ApiPage;
import org.example.api.utils.ApiTestData;
import org.example.api.utils.ConfigManager;

public class GetDetailUser_After_Updated {

    public static void main(String[] args) {
        ConfigManager.initializeConfig();
        
        String baseUrl = ConfigManager.getBaseUrl();
        ApiPage apiPage = new ApiPage();

        TestCase[] updateUser = new TestCase[] {
                new TestCase(
                        "Update success user with firstName & lastName for userID = 1",
                        ApiTestData.createRequest("url", baseUrl + "/api/users/1",
                                                                "data.first_name", "Kevin",
                                                                "data.last_name", "Cle"),
                        ApiTestData.expectedResponseOf(
                                "statusCode", 200
                        ))
        };
         apiPage.executeAndVerifyResponseFromTemplate(System.getProperty("user.dir") + "/src/data/UpdateUser.json",updateUser);


         TestCase[] getDetailUser = new TestCase[] {
                new TestCase(
                        "Get success detail user with userID = 1 after updated success",
                        ApiTestData.createRequest("url", baseUrl + "/api/users/1"),
                        ApiTestData.expectedResponseOf(
                                "statusCode", 200,
                                "data.id", 1,
                                "data.first_name", "Kevin",
                                "data.last_name","Cle"
                        ))
        };
         apiPage.executeAndVerifyResponseFromTemplate(System.getProperty("user.dir") +"/src/data/GetDetailUser.json", getDetailUser);

    }
}