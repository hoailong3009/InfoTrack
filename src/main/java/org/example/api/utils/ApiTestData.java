package org.example.api.utils;

import java.util.HashMap;
import java.util.Map;

public class ApiTestData {
    public static Map<String, Object> expectedResponseOf(Object... keyValuePairs) {
        if (keyValuePairs.length % 2 != 0) {
            throw new IllegalArgumentException("Must provide key-value pairs");
        }
        
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < keyValuePairs.length; i += 2) {
            map.put(keyValuePairs[i].toString(), keyValuePairs[i + 1]);
        }
        return map;
    }

    public static Map<String, String> createRequest(Object... keyValuePairs) {
        if (keyValuePairs.length % 2 != 0) {
            throw new IllegalArgumentException("Must provide key-value pairs");
        }
        
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < keyValuePairs.length; i += 2) {
            String key = keyValuePairs[i].toString();
            String value = keyValuePairs[i + 1].toString();
            map.put(key, value);
        }
        return map;
    }
}
