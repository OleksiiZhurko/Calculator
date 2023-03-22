package com.houarizegai.calculator.data;

public class AppDefaults {

    public static final String RESOURCE_FILE = "defaults.yaml";

    private String fontName;
    private String applicationTitle;
    private Integer windowWidth;
    private Integer windowHeight;
    private Integer buttonWidth;
    private Integer buttonHeight;
    private Integer marginX;
    private Integer marginY;

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public String getApplicationTitle() {
        return applicationTitle;
    }

    public void setApplicationTitle(String applicationTitle) {
        this.applicationTitle = applicationTitle;
    }

    public Integer getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(Integer windowWidth) {
        this.windowWidth = windowWidth;
    }

    public Integer getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(Integer windowHeight) {
        this.windowHeight = windowHeight;
    }

    public Integer getButtonWidth() {
        return buttonWidth;
    }

    public void setButtonWidth(Integer buttonWidth) {
        this.buttonWidth = buttonWidth;
    }

    public Integer getButtonHeight() {
        return buttonHeight;
    }

    public void setButtonHeight(Integer buttonHeight) {
        this.buttonHeight = buttonHeight;
    }

    public Integer getMarginX() {
        return marginX;
    }

    public void setMarginX(Integer marginX) {
        this.marginX = marginX;
    }

    public Integer getMarginY() {
        return marginY;
    }

    public void setMarginY(Integer marginY) {
        this.marginY = marginY;
    }
}
