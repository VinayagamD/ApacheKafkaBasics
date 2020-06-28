package com.vinaylogics.twitterkeyprtoection;

import com.vinaylogics.twitterkeyprtoection.factory.ElasticKey;
import com.vinaylogics.twitterkeyprtoection.factory.Key;
import com.vinaylogics.twitterkeyprtoection.factory.KeyFactory;

public class ElasticKeyProvider {
    private static final Object LOCK = new Object();
    private static ElasticKeyProvider instance;
    private final Key key;


    private ElasticKeyProvider(Key key) {
        this.key = key;
    }


    public static ElasticKeyProvider getInstance() {
        if (instance == null) {

            synchronized (LOCK) {
                if (instance == null) {
                    Key key = KeyFactory.getINSTANCE().createKey(KeyFactory.KeyType.ELASTIC);
                    instance = new ElasticKeyProvider(key);
                }
            }
        }

        return instance;
    }

    public String getHostname(){
        return key.getData(ElasticKey.ElasticKeyData.HOST_NAME);
    }
     public String getUsername(){
        return key.getData(ElasticKey.ElasticKeyData.USER_NAME);
    }
     public String getPassword(){
        return key.getData(ElasticKey.ElasticKeyData.PASSWORD);
    }


}
