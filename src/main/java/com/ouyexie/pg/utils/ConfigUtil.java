package com.ouyexie.pg.utils;

import com.ouyexie.pg.log4j.MyLogger;
import com.ouyexie.pg.log4j.MyLoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigUtil {
    private static final MyLogger LOG = MyLoggerFactory.getLogger(ConfigUtil.class);

    private static Map<String, String> configuration = new HashMap<>();

    public static Map<String, String> loadConfig() {
        Map<String, String> configMap = new HashMap<>();
        Properties config = new Properties();
        try (InputStream is = ConfigUtil.class.getResourceAsStream("/config.properties")) {
            config.load(is);
            for (Object obj : config.keySet()) {
                String key = obj.toString();
                String value = config.getProperty(key);
                configMap.put(key, value);
            }
        } catch (IOException e) {
            LOG.error("Error loading config.properties" + e);
        }
        return configMap;
    }

    public static Map<String, String> getConfig() {
        return ConfigUtil.configuration;
    }

    public static Object get(String key) {
        return ConfigUtil.configuration.get(key);
    }
}
