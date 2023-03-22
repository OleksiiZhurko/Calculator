package com.houarizegai.calculator.service;

import com.houarizegai.calculator.service.operation.BinaryChainOperation;
import com.houarizegai.calculator.view.title.Buttons;

public class Calculation {

    private final BinaryChainOperation initialOperation;
    private BinaryChainOperation lastOperation;

    public Calculation(BinaryChainOperation initialOperation) {
        this.initialOperation = initialOperation;
        this.lastOperation = initialOperation;
    }

    public Calculation addOperation(BinaryChainOperation operation) {
        lastOperation.setNextOperation(operation);
        lastOperation = operation;
        return this;
    }

    public double calculate(double firstNumber, double secondNumber, Buttons operator) {
        return initialOperation.calculate(firstNumber, secondNumber, operator);
    }
}
