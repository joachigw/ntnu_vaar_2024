package idatt2105.oving4.model;

public class Expression {
    private String firstNumber;
    private String secondNumber;
    private String operator;

    public String getFirstNumber() {
        return firstNumber;
    }

    public String getSecondNumber() {
        return secondNumber;
    }

    public String getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        return "Expression{" +
                "firstNumber='" + firstNumber + '\'' +
                ", secondNumber='" + secondNumber + '\'' +
                ", operator='" + operator + '\'' +
                '}';
    }
}
