package org.example.api.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ApiUtility {

    public static JSONObject readJsonFile(String jsonFilePath) {
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
            return new JSONObject(jsonContent);
        } catch (IOException e) {
            throw new RuntimeException("Not found JSON file: " + e.getMessage(), e);
        }
    }

    public static void updateValueByPath(JSONObject jsonObject, String path, Object value) {
        if (jsonObject == null || path == null || path.trim().isEmpty()) {
            throw new IllegalArgumentException("JSONObject và path không được null hoặc rỗng");
        }
        
        String[] keys = path.split("\\.");
        JSONObject currentObject = jsonObject;
        
        for (int i = 0; i < keys.length - 1; i++) {
            String key = keys[i];
            currentObject = navigateToNextLevel(currentObject, key);
        }
        
        String lastKey = keys[keys.length - 1];
        setValueAtPath(currentObject, lastKey, value);
    }

    private static JSONObject navigateToNextLevel(JSONObject currentObject, String key) {
        if (key.contains("[") && key.endsWith("]")) {
            return navigateToArrayElement(currentObject, key);
        } else {
            return navigateToObjectKey(currentObject, key);
        }
    }

    private static JSONObject navigateToArrayElement(JSONObject currentObject, String arrayKey) {
        String arrayName = arrayKey.substring(0, arrayKey.indexOf('['));
        int index = Integer.parseInt(arrayKey.substring(arrayKey.indexOf('[') + 1, arrayKey.indexOf(']')));
        
        if (!currentObject.has(arrayName)) {
            currentObject.put(arrayName, new JSONArray());
        }
        
        JSONArray array = currentObject.getJSONArray(arrayName);
        
        while (array.length() <= index) {
            array.put(new JSONObject());
        }
        
        if (!(array.get(index) instanceof JSONObject)) {
            array.put(index, new JSONObject());
        }
        
        return array.getJSONObject(index);
    }

    private static JSONObject navigateToObjectKey(JSONObject currentObject, String key) {
        if (!currentObject.has(key) || !(currentObject.get(key) instanceof JSONObject)) {
            currentObject.put(key, new JSONObject());
        }
        return currentObject.getJSONObject(key);
    }

    private static void setValueAtPath(JSONObject targetObject, String lastKey, Object value) {
        if (lastKey.contains("[") && lastKey.endsWith("]")) {
            setValueInArray(targetObject, lastKey, value);
        } else {
            targetObject.put(lastKey, value);
        }
    }

    private static void setValueInArray(JSONObject targetObject, String arrayKey, Object value) {
        String arrayName = arrayKey.substring(0, arrayKey.indexOf('['));
        int index = Integer.parseInt(arrayKey.substring(arrayKey.indexOf('[') + 1, arrayKey.indexOf(']')));
        
        if (!targetObject.has(arrayName)) {
            targetObject.put(arrayName, new JSONArray());
        }
        
        JSONArray array = targetObject.getJSONArray(arrayName);
        
        while (array.length() <= index) {
            array.put(JSONObject.NULL);
        }
        
        array.put(index, value);
    }
}
