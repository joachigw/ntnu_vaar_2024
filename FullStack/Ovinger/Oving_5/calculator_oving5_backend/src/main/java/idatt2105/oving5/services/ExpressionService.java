package idatt2105.oving5.services;

import idatt2105.oving5.dto.CalculationResponseDTO;
import idatt2105.oving5.model.User;
import idatt2105.oving5.model.Expression;
import idatt2105.oving5.repository.UserRepository;
import idatt2105.oving5.repository.ExpressionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
public class ExpressionService {

    private final ExpressionRepository expressionRepository;
    private final UserRepository appUserRepository;

    public ExpressionService(ExpressionRepository expressionRepository, UserRepository userRepository) {
        this.expressionRepository = expressionRepository;
        this.appUserRepository = userRepository;
    }

    public List<Expression> findAllExpressions() {
        return expressionRepository.findAll();
    }

    public List<CalculationResponseDTO> findAllExpressionsByAppUserId(Integer appUserId) {
        return expressionRepository.findByAppUserId(appUserId)
                        .stream()
                                .map(record -> {
                                    Expression expression = new Expression();
                                    expression.setId((Integer) record[0]);
                                    expression.setFirstNumber((Double) record[1]);
                                    expression.setSecondNumber((Double) record[2]);
                                    expression.setOperator((String) record[3]);
                                    expression.setResult((Double) record[4]);
                                    return new CalculationResponseDTO(expression);
                                })
                        .toList();
    }

    public void addExpression(@RequestBody Expression expression) {
        expressionRepository.save(expression);
    }

    public void assignAppUserToExpression(Expression expression, User user) {
        Expression expressionDB = expressionRepository.findById(expression.getId()).get();
        User appUser = appUserRepository.findByUsername(user.getUsername()).get();
        expression.assignAppUser(appUser);
        expressionRepository.save(expressionDB);
    }
}
