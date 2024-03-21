package idatt2105.oving5.dto;

import idatt2105.oving5.model.Expression;

public class CalculationResponseDTO {
    private Double firstNumber;
    private Double secondNumber;
    private String operator;
    private Double result;

    public CalculationResponseDTO() {
    }

    public CalculationResponseDTO(Expression expression) {
        this.firstNumber = expression.getFirstNumber();
        this.secondNumber = expression.getSecondNumber();
        this.operator = expression.getOperator();
        this.result = expression.getResult();
    }

    public Double getSecondNumber() {
        return secondNumber;
    }

    public void setSecondNumber(Double secondNumber) {
        this.secondNumber = secondNumber;
    }

    public Double getFirstNumber() {
        return firstNumber;
    }

    public void setFirstNumber(Double firstNumber) {
        this.firstNumber = firstNumber;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "CalculationResponseDTO{" +
                "current=" + secondNumber +
                ", previous=" + firstNumber +
                ", operator='" + operator + '\'' +
                ", result=" + result +
                '}';
    }
}
