package com.houarizegai.calculator.service;

import java.awt.*;
import java.util.Optional;

public class ColorConverter {

    private ColorConverter() {
        throw new AssertionError("Constructor is not allowed");
    }

    public static Color hex2Color(String colorHex) {
        return Optional.ofNullable(colorHex)
                .map(hex -> Color.decode(colorHex))
                .orElse(null);
    }
}
