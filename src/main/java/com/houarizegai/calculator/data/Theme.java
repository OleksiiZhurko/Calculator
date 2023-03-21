package com.houarizegai.calculator.data;

public class Theme {

    private String name;
    private String applicationBackground;
    private String textColor;
    private String btnEqualTextColor;
    private String operatorBackground;
    private String numbersBackground;
    private String btnEqualBackground;

    public Theme() {
    }

    private Theme(Builder builder) {
        this.name = builder.name;
        this.applicationBackground = builder.applicationBackground;
        this.textColor = builder.textColor;
        this.btnEqualTextColor = builder.btnEqualTextColor;
        this.operatorBackground = builder.operatorBackground;
        this.numbersBackground = builder.numbersBackground;
        this.btnEqualBackground = builder.btnEqualBackground;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApplicationBackground() {
        return applicationBackground;
    }

    public void setApplicationBackground(String applicationBackground) {
        this.applicationBackground = applicationBackground;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getBtnEqualTextColor() {
        return btnEqualTextColor;
    }

    public void setBtnEqualTextColor(String btnEqualTextColor) {
        this.btnEqualTextColor = btnEqualTextColor;
    }

    public String getOperatorBackground() {
        return operatorBackground;
    }

    public void setOperatorBackground(String operatorBackground) {
        this.operatorBackground = operatorBackground;
    }

    public String getNumbersBackground() {
        return numbersBackground;
    }

    public void setNumbersBackground(String numbersBackground) {
        this.numbersBackground = numbersBackground;
    }

    public String getBtnEqualBackground() {
        return btnEqualBackground;
    }

    public void setBtnEqualBackground(String btnEqualBackground) {
        this.btnEqualBackground = btnEqualBackground;
    }

    public static class Builder {
        private String name;
        private String applicationBackground;
        private String textColor;
        private String btnEqualTextColor;
        private String operatorBackground;
        private String numbersBackground;
        private String btnEqualBackground;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setApplicationBackground(String applicationBackground) {
            this.applicationBackground = applicationBackground;
            return this;
        }

        public Builder setTextColor(String textColor) {
            this.textColor = textColor;
            return this;
        }

        public Builder setBtnEqualTextColor(String btnEqualTextColor) {
            this.btnEqualTextColor = btnEqualTextColor;
            return this;
        }

        public Builder setOperatorBackground(String operatorBackground) {
            this.operatorBackground = operatorBackground;
            return this;
        }

        public Builder setNumbersBackground(String numbersBackground) {
            this.numbersBackground = numbersBackground;
            return this;
        }

        public Builder setBtnEqualBackground(String btnEqualBackground) {
            this.btnEqualBackground = btnEqualBackground;
            return this;
        }

        public Theme build() {
            return new Theme(this);
        }
    }
}
