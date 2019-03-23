package com.merpam.onenight.configuration;

public interface ConfigurationService {
    String getProperty(String key);

    void setProperty(String key, String value);
}
