package com.houarizegai.calculator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.houarizegai.calculator.data.Theme;
import com.houarizegai.calculator.data.ThemeList;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ThemeLoader {

    private static final String THEME_RESOURCE_FILE = "theme.yaml";

    private ThemeLoader() {
        throw new AssertionError("Constructor is not allowed");
    }

    public static Map<String, Theme> loadThemes() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        try (var fileStream = ThemeLoader.class.getClassLoader()
            .getResourceAsStream(THEME_RESOURCE_FILE)
        ) {
            return convertToMap(mapper.readValue(fileStream, ThemeList.class));
        } catch (IOException e) {
            return Collections.emptyMap();
        }
    }

    private static Map<String, Theme> convertToMap(ThemeList themes) {
        return themes.getThemes().stream()
            .collect(Collectors.toMap(Theme::getName, Function.identity()));
    }
}
