package idatt2105.oving5.services;

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

    public List<Expression> findAllExpressionsByAppUserId(Integer appUserId) {
        return expressionRepository.findByAppUserId(appUserId);
    }

    public void addExpression(@RequestBody Expression expression) {
        expressionRepository.save(expression);
    }

    public Expression assignAppUserToExpression(Integer expressionId, Integer appUserId) {
        Expression expression = expressionRepository.findById(expressionId).get();
        User appUser = appUserRepository.findById(appUserId).get();
        expression.assignAppUser(appUser);
        return expressionRepository.save(expression);
    }
}
