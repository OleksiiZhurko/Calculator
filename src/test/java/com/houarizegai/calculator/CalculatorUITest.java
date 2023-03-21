package com.houarizegai.calculator;

import com.houarizegai.calculator.view.CalculatorUI;
import com.houarizegai.calculator.view.title.Buttons;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorUITest {

    private CalculatorUI calculatorUI;

    @BeforeEach
    void setUp() {
        calculatorUI = new CalculatorUI();
    }

    @ParameterizedTest
    @MethodSource("provideCalculationArguments")
    void testCalculation(double firstNumber, double secondNumber, Buttons operator, double expectedResult) {
        assertEquals(expectedResult, calculatorUI.calculate(firstNumber, secondNumber, operator));
    }

    private static Stream<Arguments> provideCalculationArguments() {
        return Stream.of(
                Arguments.of(3, 5, Buttons.ADD, 8),
                Arguments.of(2, 8, Buttons.MINUS, -6),
                Arguments.of(44.5, 10, Buttons.MULT, 445),
                Arguments.of(320, 5, Buttons.DIV, 64),
                Arguments.of(3, 5, Buttons.PERCENT, 3),
                Arguments.of(5, 3, Buttons.POW, 125)
        );
    }
}
