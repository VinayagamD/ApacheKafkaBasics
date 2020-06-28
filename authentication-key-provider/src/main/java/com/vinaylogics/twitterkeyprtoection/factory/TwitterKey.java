package com.vinaylogics.twitterkeyprtoection.factory;

import com.vinaylogics.twitterkeyprtoection.exception.InvalidKeyException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TwitterKey extends Key {


    public enum TwitterKeyData implements KeyData{
        CONSUMER_KEY("CONSUMER_KEY"),
        CONSUMER_SECRET("CONSUMER_SECRET"),
        API_TOKEN("API_TOKEN"),
        API_SECRET("API_SECRET");

        private final String key;
        TwitterKeyData(String key) {
            this.key = key;
        }

        @Override
        public String getKey() {
            return key;
        }
    }

    public TwitterKey(KeyFactory.KeyType keyType) {
        super(keyType);
    }

    @Override
    void loadProperties() {
        try(InputStream inputStream = TwitterKey.class.getClassLoader().getResourceAsStream("twitter_key.properties")){
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
           throw new InvalidKeyException(e.getMessage());
        }
    }

}
