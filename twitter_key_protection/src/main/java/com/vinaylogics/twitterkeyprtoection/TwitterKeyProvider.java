package com.vinaylogics.twitterkeyprtoection;

import com.vinaylogics.twitterkeyprtoection.factory.Key;
import com.vinaylogics.twitterkeyprtoection.factory.KeyFactory;

import java.io.IOException;
public class TwitterKeyProvider {
    private static final Object LOCK = new Object();
    private static TwitterKeyProvider instance;
    private final Key key;


    private TwitterKeyProvider(Key key) {
        this.key = key;
    }


    public static TwitterKeyProvider getInstance() throws IOException {
        if (instance == null) {

            synchronized (LOCK) {
                if (instance == null) {
                    Key key = KeyFactory.getINSTANCE().createKey(KeyFactory.KeyType.TWITTER);
                    key.loadKey();
                    instance = new TwitterKeyProvider(key);
                }
            }
        }

        return instance;
    }

    public String getConsumerKey() {
        return key.getKey(Key.ApiKey.CONSUMER_KEY);
    }

    public String getConsumerSecret() {
        return key.getKey(Key.ApiKey.CONSUMER_SECRET);
    }

    public String getApiToken() {
        return key.getKey(Key.ApiKey.API_TOKEN);
    }

    public String getApiSecret() {
        return key.getKey(Key.ApiKey.API_SECRET);
    }


}
