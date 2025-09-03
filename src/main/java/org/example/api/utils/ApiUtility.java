package org.example.api.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class ApiUtility {

    public static JSONObject readJsonFile(String jsonFilePath) {
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
            JSONObject jsonObject = new JSONObject(jsonContent);
            
            // Auto-replace URL with environment base URL
            updateUrlForEnvironment(jsonObject);
            
            return jsonObject;
        } catch (IOException e) {
            throw new RuntimeException("Not found JSON file: " + e.getMessage(), e);
        }
    }

    public static void updateValueByPath(JSONObject jsonObject, String path, Object value) {
        String[] keys = path.split("\\.");
        JSONObject current = jsonObject;
        
        // Navigate to parent objects
        for (int i = 0; i < keys.length - 1; i++) {
            String key = keys[i];
            if (!current.has(key)) {
                current.put(key, new JSONObject());
            }
            current = current.getJSONObject(key);
        }
        
        // Set final value
        current.put(keys[keys.length - 1], value);
    }
    
    private static void updateUrlForEnvironment(JSONObject jsonObject) {
        if (jsonObject.has("url")) {
            String originalUrl = jsonObject.getString("url");
            String baseUrl = ConfigManager.getBaseUrl();
            
            // Extract path from original URL (everything after domain)
            if (originalUrl.contains("://")) {
                String path = extractPathFromUrl(originalUrl);
                String newUrl = baseUrl + path;
                jsonObject.put("url", newUrl);
                
                System.out.println("URL được cập nhật từ config [" + ConfigManager.getEnvironment() + "]: " + newUrl);
            }
        }
    }
    
    private static String extractPathFromUrl(String url) {
        // Extract path from URL: https://reqres.in/api/users -> /api/users
        try {
            int domainStart = url.indexOf("://") + 3;
            int pathStart = url.indexOf('/', domainStart);
            return pathStart > 0 ? url.substring(pathStart) : "";
        } catch (Exception e) {
            return "";
        }
    }
}
