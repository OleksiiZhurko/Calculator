package com.houarizegai.calculator.service.operation;

import com.houarizegai.calculator.view.title.Buttons;

import java.util.function.BiFunction;

public class BinaryOperation implements BinaryChainOperation {

    private final Buttons operation;
    private final BiFunction<Double, Double, Double> function;

    private BinaryChainOperation nextOperation = EmptyOperation.getInstance();

    public BinaryOperation(Buttons operation, BiFunction<Double, Double, Double> function) {
        this.operation = operation;
        this.function = function;
    }

    @Override
    public void setNextOperation(BinaryChainOperation nextOperation) {
        this.nextOperation = nextOperation;
    }

    @Override
    public double calculate(double firstNumber, double secondNumber, Buttons operator) {
        if (operation.equals(operator)) {
            return function.apply(firstNumber, secondNumber);
        }
        return nextOperation.calculate(firstNumber, secondNumber, operator);
    }
}
