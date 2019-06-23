package com.merpam.onenight.configuration.impl;

import com.merpam.onenight.configuration.ConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Properties;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationServiceImpl.class);

    @Value(value = "classpath:${properties.path}")
    private Resource propertiesResource;

    private final Properties properties = new Properties();

    @PostConstruct
    public void init() {
        try {
            properties.load(propertiesResource.getInputStream());
        } catch (IOException e) {
            LOG.error("Cannot find properties path", e);
        }
    }

    @Override
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    @Override
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }
}
