package com.houarizegai.calculator.service;

import java.awt.Color;
import java.util.Optional;

public class ColorConverter {

    public static Color hex2Color(String colorHex) {
        return Optional.ofNullable(colorHex)
                .map(hex -> Color.decode(colorHex))
                .orElse(null);
    }
}
