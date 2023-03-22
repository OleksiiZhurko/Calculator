package com.houarizegai.calculator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;

public class ConfigLoader {

    public <T> T loadConfig(String filename, Class<T> obj) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        try (var fileStream = ConfigLoader.class.getClassLoader()
                .getResourceAsStream(filename)
        ) {
            return mapper.readValue(fileStream, obj);
        } catch (IOException e) {
            return null;
        }
    }
}
