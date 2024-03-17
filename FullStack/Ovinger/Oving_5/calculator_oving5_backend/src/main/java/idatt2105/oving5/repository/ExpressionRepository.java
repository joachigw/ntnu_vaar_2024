package idatt2105.oving5.repository;

import idatt2105.oving5.model.Expression;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpressionRepository
        extends JpaRepository<Expression, Integer> {
}
