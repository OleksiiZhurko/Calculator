package com.houarizegai.calculator.controller;

import com.houarizegai.calculator.data.AppDefaults;
import com.houarizegai.calculator.data.Theme;
import com.houarizegai.calculator.data.ThemeList;
import com.houarizegai.calculator.service.Calculation;
import com.houarizegai.calculator.service.ConfigLoader;
import com.houarizegai.calculator.service.operation.BinaryOperation;
import com.houarizegai.calculator.service.operation.OperationFunc;
import com.houarizegai.calculator.view.CalculatorUI;
import com.houarizegai.calculator.view.title.Buttons;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Controller {

    private final ConfigLoader configLoader = new ConfigLoader();

    public void start() {
        CalculatorUI calculatorUI = new CalculatorUI(
                new Calculation(new BinaryOperation(Buttons.ADD, OperationFunc::add))
                        .addOperation(new BinaryOperation(Buttons.MINUS, OperationFunc::minus))
                        .addOperation(new BinaryOperation(Buttons.MULT, OperationFunc::mult))
                        .addOperation(new BinaryOperation(Buttons.DIV, OperationFunc::div))
                        .addOperation(new BinaryOperation(Buttons.PERCENT, OperationFunc::percent))
                        .addOperation(new BinaryOperation(Buttons.POW, OperationFunc::pow)),
                configLoader.loadConfig(AppDefaults.RESOURCE_FILE, AppDefaults.class),
                loadThemes()
        );
        calculatorUI.start();
    }

    private Map<String, Theme> loadThemes() {
        return Optional.ofNullable(configLoader.loadConfig(ThemeList.RESOURCE_FILE, ThemeList.class))
                .map(themes -> themes.getThemes()
                        .stream()
                        .collect(Collectors.toMap(Theme::getName, Function.identity())))
                .orElse(Collections.emptyMap());
    }
}
