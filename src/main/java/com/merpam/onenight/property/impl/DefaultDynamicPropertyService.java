package com.merpam.onenight.property.impl;

import com.merpam.onenight.property.DynamicPropertyService;
import org.apache.commons.lang.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Properties;

@Service
public class DefaultDynamicPropertyService implements DynamicPropertyService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultDynamicPropertyService.class);

    @Value("${configuration.service.properties.path}")
    private String propertiesResourcePath;

    @Qualifier("webApplicationContext")
    @Autowired
    private ResourceLoader resourceLoader;

    private final Properties properties = new Properties();

    @PostConstruct
    public void init() {
        try {
            Resource propertiesResource = resourceLoader.getResource(String.format("classpath:%s", propertiesResourcePath));
            properties.load(propertiesResource.getInputStream());
            properties.replaceAll((k, v) -> StrSubstitutor.replace(v, System.getenv()));
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
