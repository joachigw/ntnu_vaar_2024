package idatt2105.oving5.controller;

import idatt2105.oving5.model.Expression;
import idatt2105.oving5.services.ExpressionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expressions")
public class ExpressionController {

    private final ExpressionService expressionService;

    public ExpressionController(ExpressionService expressionService) {
        this.expressionService = expressionService;
    }

    @GetMapping("/")
    public List<Expression> getExpressions() {
        return expressionService.findAllExpressions();
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
