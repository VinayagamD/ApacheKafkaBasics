package com.vinaylogics.twitterkeyprtoection.factory;

import com.vinaylogics.twitterkeyprtoection.exception.InvalidKeyException;

public class KeyFactory {

    private static final KeyFactory INSTANCE = new KeyFactory();

    private KeyFactory(){
    }

    public static KeyFactory getINSTANCE() {
        return INSTANCE;
    }

    public enum KeyType  {
        TWITTER,
        ELASTIC;

    }

    public Key createKey(KeyType type){
        Key key;
        switch (type){
            case TWITTER:
                key = new TwitterKey(type);
                break;
            case ELASTIC:
                key = new ElasticKey(type);
                break;
            default:
                throw new InvalidKeyException("Invalid key type");
        }
        return key;
    }
}
