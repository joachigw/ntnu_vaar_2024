package idatt2105.oving5.repository;

import idatt2105.oving5.model.Expression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpressionRepository extends JpaRepository<Expression, Integer> {
    List<Expression> findByAppUserId(Integer appUserId);
}
