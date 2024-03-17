package idatt2105.oving5.controller;

import idatt2105.oving5.model.AppUser;
import idatt2105.oving5.model.Expression;
import idatt2105.oving5.services.AppUserService;
import idatt2105.oving5.services.ExpressionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expressions")
public class ExpressionController {

    private final ExpressionService expressionService;
    private final AppUserService appUserService;

    public ExpressionController(ExpressionService expressionService, AppUserService appUserService) {
        this.expressionService = expressionService;
        this.appUserService = appUserService;
    }

    @GetMapping("/")
    public List<Expression> getExpressions() {
        return expressionService.findAllExpressions();
    }

    @GetMapping("/appuser")
    public List<Expression> getExpressions(@RequestBody AppUser appUser) {
        Integer appUserId = appUserService.findAppUserByUsername(appUser.getUsername()).getId();
        return expressionService.findAllExpressionsByAppUserId(appUserId);
    }

    @PostMapping("/")
    public void addExpression(@RequestBody Expression expression) {
        expressionService.addExpression(expression);
    }

    @PutMapping("/{expressionId}/appuser/{appUserId}")
    public Expression assignAppUserToExpression(@PathVariable Integer expressionId, @PathVariable Integer appUserId) {
        return expressionService.assignAppUserToExpression(expressionId, appUserId);
    }
}
