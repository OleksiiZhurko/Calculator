package com.houarizegai.calculator.service.operation;

public interface OperationFunc {

    static double add(double a, double b) {
        return a + b;
    }

    static double minus(double a, double b) {
        return a - b;
    }

    static double mult(double a, double b) {
        return a * b;
    }

    static double div(double a, double b) {
        return a / b;
    }

    static double percent(double a, double b) {
        return a % b;
    }

    static double pow(double a, double b) {
        return Math.pow(a, b);
    }
}
