package idatt2105.oving5.model;

public class Calculator {
    public Calculator() {
    }

    public double calculate(Expression expression) {

        double firstNumber = expression.getFirstNumber();
        double secondNumber = expression.getSecondNumber();
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
