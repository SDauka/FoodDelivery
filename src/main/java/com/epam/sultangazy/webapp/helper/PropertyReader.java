package com.epam.sultangazy.webapp.helper;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    private final Logger LOG = Logger.getLogger(PropertyReader.class);
    private Properties properties = new Properties();
    public static final String PAGES_PROPERTIES = "pages.properties";
    public static final String DB_CONFIG = "database.properties";
    public static final String IMAGE_PATH = "imagePath.properties";
    public static final String SUBSTRING = "substring.properties";

    public PropertyReader(String fileName) {
        InputStream inputStream = PropertyReader.class.getClassLoader().getResourceAsStream(fileName);
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            LOG.error(" Property Exception" + e);
        }
    }

    public String getProperties(String key) {
        return properties.getProperty(key);
    }
}
