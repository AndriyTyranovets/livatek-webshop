package com.github.andriytyranovets.webshop.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileReader;
import java.io.IOException;

public final class ConfigLoader {
    private static ObjectMapper mapper;

    public static <T> T loadConfig(String configFile, TypeReference<T> tr) {
        var mapper = getMapper();
        try(var reader = new FileReader(configFile)) {
            return mapper.readValue(reader, tr);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static ObjectMapper getMapper() {
        if(mapper == null) {
            mapper = new ObjectMapper();
        }
        return mapper;
    }
}
