package com.merpam.onenight.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

public final class TestUtils {

    private TestUtils() {
        //private constructor, not meant to be initialized
    }

    public static <T> T loadData(String resourcePath, Class<T> valueType) {
        try {
            Resource resource = new ClassPathResource(resourcePath);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(resource.getFile(), valueType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String convertToJson(Object value) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
