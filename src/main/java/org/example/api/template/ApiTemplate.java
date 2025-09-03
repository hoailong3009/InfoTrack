package org.example.api.template;

import io.cucumber.datatable.DataTable;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.api.utils.ApiUtility;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ApiTemplate {
    
    private String filePath;
    private RequestSpecification requestSpec;

    public ApiTemplate(String filePath) {
        this.filePath = filePath;
        this.requestSpec = RestAssured.given();
    }

    public Response doExecute(DataTable table) {
        JSONObject apiConfig = ApiUtility.readJsonFile(filePath);
        
        if (table != null) {
            apiConfig = updateConfigFromDataTable(apiConfig, table);
        }
        
        String url = apiConfig.getString("url");
        String method = apiConfig.getString("method").toLowerCase();
        JSONObject headers = apiConfig.has("headers") ? apiConfig.getJSONObject("headers") : new JSONObject();
        JSONObject data = apiConfig.has("data") ? apiConfig.getJSONObject("data") : new JSONObject();
        
        requestSpec = RestAssured.given();
        
        headers.keys().forEachRemaining(key -> requestSpec.header(key, headers.getString(key)));
        
        if (data.length() > 0) {
            requestSpec.body(data.toString());
        }
        
        printRequestInfo(url, method, headers, data);
        
        Response response;
        switch (method) {
            case "get":
                response = requestSpec.get(url);
                break;
            case "post":
                response = requestSpec.post(url);
                break;
            case "put":
                response = requestSpec.put(url);
                break;
            case "delete":
                response = requestSpec.delete(url);
                break;
            default:
                throw new IllegalArgumentException("Not support method: " + method);
        }
        
        System.out.println("Status code: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody().asPrettyString());
        
        return response;
    }

    private JSONObject updateConfigFromDataTable(JSONObject apiConfig, DataTable table) {
        if (table == null) {
            return apiConfig;
        }
        
        List<List<String>> rows = table.cells();
        
        if (rows.size() > 1) {
            List<String> headers = rows.get(0);
            
            int pathIndex = headers.indexOf("path");
            int valueIndex = headers.indexOf("value");
            
            if (pathIndex >= 0 && valueIndex >= 0) {
                for (int i = 1; i < rows.size(); i++) {
                    List<String> row = rows.get(i);
                    if (row.size() > Math.max(pathIndex, valueIndex)) {
                        String path = row.get(pathIndex);
                        String value = row.get(valueIndex);
                        
                        if (path != null && !path.isEmpty() && value != null) {
                            ApiUtility.updateValueByPath(apiConfig, path, value);
                        }
                    }
                }
            }
        }
        
        return apiConfig;
    }
    

    public static DataTable createDataTableFromMap(Map<String, String> pathValueMap) {
        List<List<String>> rows = new ArrayList<>();
        rows.add(List.of("path", "value"));
        
        for (Map.Entry<String, String> entry : pathValueMap.entrySet()) {
            rows.add(List.of(entry.getKey(), entry.getValue()));
        }
        
        return DataTable.create(rows);
    }

    private void printRequestInfo(String url, String method, JSONObject headers, JSONObject data) {
        System.out.println("\n===== Request =====");
        System.out.println("URL: " + url);
        System.out.println("Method: " + method.toUpperCase());
        
        System.out.println("\nHeaders:");
        if (headers != null && headers.length() > 0) {
            headers.keys().forEachRemaining(key -> {
                System.out.println("  " + key + ": " + headers.getString(key));
            });
        }
        System.out.println("\nBody:");
        if (data != null && data.length() > 0) {
            System.out.println(data.toString(2)); 
        } 
        System.out.println("===============================\n");
    }
}
