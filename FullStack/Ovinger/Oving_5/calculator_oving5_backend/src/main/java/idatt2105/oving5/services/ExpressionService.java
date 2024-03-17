package idatt2105.oving5.services;

import idatt2105.oving5.model.AppUser;
import idatt2105.oving5.model.Expression;
import idatt2105.oving5.repository.AppUserRepository;
import idatt2105.oving5.repository.ExpressionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
public class ExpressionService {

    private final ExpressionRepository expressionRepository;
    private final AppUserRepository appUserRepository;

    public ExpressionService(ExpressionRepository expressionRepository, AppUserRepository appUserRepository) {
        this.expressionRepository = expressionRepository;
        this.appUserRepository = appUserRepository;
    }

    public List<Expression> findAllExpressions() {
        return expressionRepository.findAll();
    }

    public void addExpression(@RequestBody Expression expression) {
        expressionRepository.save(expression);
    }

    public Expression assignAppUserToExpression(Integer expressionId, Integer appUserId) {
        Expression expression = expressionRepository.findById(expressionId).get();
        AppUser appUser = appUserRepository.findById(appUserId).get();
        expression.assignAppUser(appUser);
        return expressionRepository.save(expression);
    }
}
