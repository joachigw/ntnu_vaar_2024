package idatt2105.oving5.controller;

import idatt2105.oving5.dto.CalculationResponseDTO;
import idatt2105.oving5.dto.ExpressionsResponseDTO;
import idatt2105.oving5.model.User;
import idatt2105.oving5.model.Expression;
import idatt2105.oving5.services.UserService;
import idatt2105.oving5.services.ExpressionService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expression")
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

    @PostMapping("/user")
    public ExpressionsResponseDTO getExpressions(@RequestBody User appUser) {
        User foundUser = appUserService.findAppUserByUsername(appUser.getUsername());
        if (foundUser == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List<CalculationResponseDTO> expressionList = expressionService.findAllExpressionsByAppUserId(foundUser.getId());
        return new ExpressionsResponseDTO(expressionList);
    }
}
