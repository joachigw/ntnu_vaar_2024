package idatt2105.oving4.model;

import idatt2105.oving4.controller.CalculatorController;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Calculator {
    public Calculator() {
    }

    public double calculate(Expression expression) {

        double firstNumber = Double.parseDouble(expression.getFirstNumber());
        double secondNumber = Double.parseDouble(expression.getSecondNumber());
        String operator = expression.getOperator();

        return switch (operator) {
            case "+" -> firstNumber + secondNumber;
            case "-" -> firstNumber - secondNumber;
            case "x" -> firstNumber * secondNumber;
            case "/" -> {
                if (secondNumber == 0) {
                    throw new ArithmeticException("Division by zero is not allowed.");
                }
                yield firstNumber / secondNumber;
            }
            default -> throw new IllegalArgumentException("Invalid operator: " + operator);
        };

    }
}
