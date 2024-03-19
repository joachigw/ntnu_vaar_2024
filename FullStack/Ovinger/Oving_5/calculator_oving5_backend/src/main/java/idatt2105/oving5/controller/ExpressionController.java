package idatt2105.oving5.controller;

import idatt2105.oving5.model.User;
import idatt2105.oving5.model.Expression;
import idatt2105.oving5.services.UserService;
import idatt2105.oving5.services.ExpressionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expressions")
@CrossOrigin(origins = "http://localhost:5173")
public class ExpressionController {

    private final ExpressionService expressionService;
    private final UserService appUserService;

    public ExpressionController(ExpressionService expressionService, UserService userService) {
        this.expressionService = expressionService;
        this.appUserService = userService;
    }

    @GetMapping("/")
    public List<Expression> getExpressions() {
        return expressionService.findAllExpressions();
    }

    @PostMapping("/")
    public void addExpression(@RequestBody Expression expression) {
        expressionService.addExpression(expression);
    }

    @GetMapping("/user")
    public List<Expression> getExpressions(@RequestBody User appUser) {
        Integer appUserId = appUserService.findAppUserByUsername(appUser.getUsername()).getId();
        return expressionService.findAllExpressionsByAppUserId(appUserId);
    }

    @PutMapping("/{expressionId}/appuser/{appUserId}")
    public Expression assignAppUserToExpression(@PathVariable Integer expressionId, @PathVariable Integer appUserId) {
        return expressionService.assignAppUserToExpression(expressionId, appUserId);
    }
}
