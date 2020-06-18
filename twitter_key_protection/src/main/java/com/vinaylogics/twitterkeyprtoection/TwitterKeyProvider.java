package com.vinaylogics.twitterkeyprtoection;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Period;
import java.util.Base64;
import java.util.Properties;

public class TwitterKeyProvider {
    private static final Object LOCK = new Object();
    private static TwitterKeyProvider instance;

    private final Properties properties;

    private TwitterKeyProvider(Properties properties) {
        this.properties = properties;
    }

    public static TwitterKeyProvider getInstance() throws IOException {
        if(instance == null) {

            synchronized (LOCK) {
                if (instance == null) {
                    try (InputStream inputStream = TwitterKeyProvider.class.getClassLoader().getResourceAsStream("twitter_key.properties")) {
                        Properties properties = new Properties();
                        properties.load(inputStream);
                        instance = new TwitterKeyProvider(properties);
                    }
                }
            }
        }

        return instance;
    }

    public enum Keys{
        PROTECTION_KEY("protection_key"),
        CONSUMER_KEY("CONSUMER_KEY"),
        CONSUMER_SECRET("CONSUMER_SECRET"),
        API_TOKEN("API_TOKEN"),
        API_SECRET("API_SECRET");

        private final String key;
        private static final String KEY_SPLIT = ";";

        Keys(String key) {
            this.key = key;
        }

        public String decodeString(String encodeString){
            return new String(Base64.getDecoder().decode(encodeString), StandardCharsets.UTF_8).split(KEY_SPLIT)[1];
        }
    }


}
