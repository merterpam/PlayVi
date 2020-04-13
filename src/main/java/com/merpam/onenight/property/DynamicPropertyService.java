package com.merpam.onenight.property;

public interface DynamicPropertyService {
    String getProperty(String key);

    void setProperty(String key, String value);
}
