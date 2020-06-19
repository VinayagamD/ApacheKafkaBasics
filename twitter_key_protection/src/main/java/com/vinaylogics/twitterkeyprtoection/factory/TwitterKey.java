package com.vinaylogics.twitterkeyprtoection.factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TwitterKey implements Key{

    private Properties properties;


    @Override
    public String getFileName() {
        return "twitter_key.properties";
    }

    @Override
    public void loadKey() throws IOException {
        try(InputStream inputStream = TwitterKey.class.getClassLoader().getResourceAsStream(getFileName())){
            properties = new Properties();
            properties.load(inputStream);
        }
    }

    @Override
    public String getKey(ApiKey key) {
        return key.decodeString(properties);
    }
}
