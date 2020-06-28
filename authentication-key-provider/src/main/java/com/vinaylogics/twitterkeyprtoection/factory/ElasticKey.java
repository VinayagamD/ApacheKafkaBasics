package com.vinaylogics.twitterkeyprtoection.factory;

import com.vinaylogics.twitterkeyprtoection.exception.InvalidKeyException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ElasticKey extends Key {


    public ElasticKey(KeyFactory.KeyType keyType) {
        super(keyType);
        loadProperties();
    }

    public enum ElasticKeyData  implements Key.KeyData{
        HOST_NAME("HOST_NAME"),
        USER_NAME("USER_NAME"),
        PASSWORD("PASSWORD");

        private final String key;

        ElasticKeyData(String key) {
            this.key = key;
        }

        @Override
        public String getKey() {
            return key;
        }
    }

    @Override
    void loadProperties() {
        try(InputStream inputStream = TwitterKey.class.getClassLoader().getResourceAsStream("elastic_key.properties")){
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            throw new InvalidKeyException(e.getMessage());
        }
    }



}
