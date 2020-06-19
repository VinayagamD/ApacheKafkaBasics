package com.vinaylogics.twitterkeyprtoection.factory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Properties;

public interface Key {

    enum ApiKey{
        CONSUMER_KEY("CONSUMER_KEY"),
        CONSUMER_SECRET("CONSUMER_SECRET"),
        API_TOKEN("API_TOKEN"),
        API_SECRET("API_SECRET");

        private static final String PROTECTION_KEY = "protection_key";
        private static final String SPLITTER_STRING = ";";
        private final String key;
        ApiKey(String key) {
            this.key = key;
        }

        private String getString(Properties properties){
            return properties.getProperty(key);
        }

        public String decodeString(Properties properties){
            return new String(Base64.getDecoder().decode(getString(properties)), StandardCharsets.UTF_8).split(SPLITTER_STRING)[1];
        }

    }

    String getFileName();
    void loadKey() throws IOException;
    String getKey(ApiKey key);
}
