package idatt2105.oving5.dto;

import idatt2105.oving5.model.Expression;
import idatt2105.oving5.model.User;

public class CalculationRequestDTO {
    private Expression expression;
    private User user;

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "CalculationRequestDTO{" +
                "expression=" + expression +
                ", user=" + user +
                '}';
    }
}
