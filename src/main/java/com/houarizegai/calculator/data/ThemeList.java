package com.houarizegai.calculator.data;

import java.util.List;

public class ThemeList {

    public static final String RESOURCE_FILE = "theme.yaml";

    private List<Theme> themes;

    public List<Theme> getThemes() {
        return themes;
    }

    public void setThemes(List<Theme> themes) {
        this.themes = themes;
    }
}
