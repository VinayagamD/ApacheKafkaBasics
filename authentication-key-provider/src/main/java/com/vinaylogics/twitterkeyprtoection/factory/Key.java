package com.vinaylogics.twitterkeyprtoection.factory;

import com.vinaylogics.twitterkeyprtoection.exception.InvalidKeyException;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Properties;

public abstract class Key {

    protected Properties properties;
    private KeyFactory.KeyType keyType;



    public Key(KeyFactory.KeyType keyType) {
        this.keyType = keyType;
        loadProperties();
    }

    public interface KeyData{
        String SPLITTER_STRING = ";";

        String getKey();

        default String decodeString(Properties properties){
            return new String(Base64.getDecoder().decode(properties.getProperty(getKey())), StandardCharsets.UTF_8).split(SPLITTER_STRING)[1];
        }
    }

    abstract void loadProperties();


    public String getData(KeyData keyData){
        if(keyData == null)
            throw new InvalidKeyException("KeyDataNotSet");
        return keyData.decodeString(properties);
    }

}
