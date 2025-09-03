package org.example.api.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    
    private Properties prop;
    
    public void initializeConfig() throws IOException {
        prop = new Properties();
        FileInputStream fis = new FileInputStream(
            System.getProperty("user.dir") + "/src/main/java/org/example/api/resources/config.properties");
        prop.load(fis);
    }
    
    public String getEnvironment() {
        return prop.getProperty("env");
    }
    
    public String getBaseUrl() {
        String env = getEnvironment().toLowerCase();
        return prop.getProperty(env + ".base.url");
    }
}
