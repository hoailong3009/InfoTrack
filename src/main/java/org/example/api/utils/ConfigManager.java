package org.example.api.utils;

import java.io.IOException;

public class ConfigManager {
    
    private static ConfigReader config;
    private static boolean isInitialized = false;
    
    public static void initializeConfig() {
        if (!isInitialized) {
            try {
                config = new ConfigReader();
                config.initializeConfig();
                isInitialized = true;
                System.out.println("Environment: " + config.getEnvironment());
            } catch (IOException e) {
                throw new RuntimeException("Error read file config: " + e.getMessage(), e);
            }
        }
    }
    
    public static String getEnvironment() {
        return config.getEnvironment();
    }

    public static String getBaseUrl() {
        return config.getBaseUrl();
    }
}
