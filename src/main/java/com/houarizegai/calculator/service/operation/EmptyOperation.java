package com.houarizegai.calculator.service.operation;

import com.houarizegai.calculator.view.title.Buttons;

public class EmptyOperation implements BinaryChainOperation {

    private static final BinaryChainOperation instance = new EmptyOperation();

    public static BinaryChainOperation getInstance() {
        return instance;
    }

    @Override
    public void setNextOperation(BinaryChainOperation nextOperation) {
        throw new UnsupportedOperationException();
    }

    @Override
    public double calculate(double firstNumber, double secondNumber, Buttons operator) {
        return secondNumber;
    }
}
