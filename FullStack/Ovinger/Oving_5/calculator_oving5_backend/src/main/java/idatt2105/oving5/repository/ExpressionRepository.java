package idatt2105.oving5.repository;

import idatt2105.oving5.model.Expression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpressionRepository extends JpaRepository<Expression, Integer> {
    @Query("SELECT e.id, e.firstNumber, e.secondNumber, e.operator, e.result, u.id FROM Expression e " +
            "LEFT JOIN User u ON e.appUser.id = u.id WHERE e.appUser.id = :appUserId")
    List<Object[]> findByAppUserId(@Param("appUserId") Integer appUserId);
}
