package com.houarizegai.calculator.service.operation;

import com.houarizegai.calculator.view.title.Buttons;

public interface BinaryChainOperation {

    void setNextOperation(BinaryChainOperation nextOperation);

    double calculate(double firstNumber, double secondNumber, Buttons operator);
}
