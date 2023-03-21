package com.houarizegai.calculator.view.title;

public enum Buttons {

    RESET("C"),
    BACKSPACE("<-"),
    PERCENT("%"),
    POW("pow"),
    SQRT("√"),
    LN("ln"),
    DIV("÷"),
    MULT("×"),
    MINUS("-"),
    ADD("+"),
    EQUAL("="),
    DOT("."),
    ZERO("0"),
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    NOTHING("");

    public final String title;

    Buttons(String title) {
        this.title = title;
    }
}
