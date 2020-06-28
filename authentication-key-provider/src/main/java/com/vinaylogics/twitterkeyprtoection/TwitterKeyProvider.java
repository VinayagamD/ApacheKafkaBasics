package com.vinaylogics.twitterkeyprtoection;

import com.vinaylogics.twitterkeyprtoection.factory.Key;
import com.vinaylogics.twitterkeyprtoection.factory.KeyFactory;
import com.vinaylogics.twitterkeyprtoection.factory.TwitterKey;

import java.io.IOException;
public class TwitterKeyProvider {
    private static final Object LOCK = new Object();
    private static TwitterKeyProvider instance;
    private final Key key;


    private TwitterKeyProvider(Key key) {
        this.key = key;
    }


    public static TwitterKeyProvider getInstance() {
        if (instance == null) {

            synchronized (LOCK) {
                if (instance == null) {
                    Key key = KeyFactory.getINSTANCE().createKey(KeyFactory.KeyType.TWITTER);
                    instance = new TwitterKeyProvider(key);
                }
            }
        }

        return instance;
    }

    public String getConsumerKey() {
        return key.getData(TwitterKey.TwitterKeyData.CONSUMER_KEY);
    }

    public String getConsumerSecret() {
        return key.getData(TwitterKey.TwitterKeyData.CONSUMER_SECRET);
    }

    public String getApiToken() {
        return key.getData(TwitterKey.TwitterKeyData.API_TOKEN);
    }

    public String getApiSecret() {
        return key.getData(TwitterKey.TwitterKeyData.API_SECRET);
    }


}
