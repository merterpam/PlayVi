package com.merpam.onenight.configuration;

public interface DynamicPropertyService {
    String getProperty(String key);

    void setProperty(String key, String value);
}
