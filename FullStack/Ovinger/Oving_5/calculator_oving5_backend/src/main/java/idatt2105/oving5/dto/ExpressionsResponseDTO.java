package idatt2105.oving5.dto;

import java.util.List;

public class ExpressionsResponseDTO {
    private List<CalculationResponseDTO> expressionList;

    public ExpressionsResponseDTO(List<CalculationResponseDTO> expressionList) {
        this.expressionList = expressionList;
    }

    public List<CalculationResponseDTO> getExpressionList() {
        return expressionList;
    }

    public void setExpressionList(List<CalculationResponseDTO> expressionList) {
        this.expressionList = expressionList;
    }
}
