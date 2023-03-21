package com.houarizegai.calculator.theme;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.houarizegai.calculator.theme.properties.Theme;
import com.houarizegai.calculator.theme.properties.ThemeList;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

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
            ThemeList themeList = mapper.readValue(fileStream, ThemeList.class);
            return themeList.getThemesAsMap();
        } catch (IOException e) {
            return Collections.emptyMap();
        }
    }
}
