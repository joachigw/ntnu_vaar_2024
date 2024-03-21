package idatt2105.oving5.controller;

import idatt2105.oving5.dto.CalculationRequestDTO;
import idatt2105.oving5.dto.CalculationResponseDTO;
import idatt2105.oving5.model.Expression;
import idatt2105.oving5.model.Calculator;
import idatt2105.oving5.model.User;
import idatt2105.oving5.services.ExpressionService;
import idatt2105.oving5.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/calculator")
@CrossOrigin(origins = "http://localhost:5173")
public class CalculatorController {

    private final ExpressionService expressionService;
    private final UserService userService;

    public CalculatorController(ExpressionService expressionService, UserService userService) {
        this.expressionService = expressionService;
        this.userService = userService;
    }


    @PostMapping("/calculate")
    public CalculationResponseDTO calculateExpression(@RequestBody CalculationRequestDTO requestDTO) {

        Calculator calculator = new Calculator();
        Logger logger = LoggerFactory.getLogger(CalculatorController.class);

        try {
            Expression expression = requestDTO.getExpression();
            User user = userService.findAppUserByUsername(requestDTO.getUser().getUsername());

            logger.info("Calculating the following expression: " + expression);

            double result = calculator.calculate(expression);
            logger.info("Saving expression to database...");
            expression.setResult(result);
            expressionService.addExpression(expression);
            expressionService.assignAppUserToExpression(expression, user);
            logger.info("Saved expression with id " + expression.getId() + " to user " + user.getUsername());

            CalculationResponseDTO responseDTO = new CalculationResponseDTO(expression);
            logger.info("Sending the following response: " + responseDTO);
            responseDTO.setResult(result);

            return responseDTO;
        } catch (Exception e) {
            logger.error("There was an error with the calculation of the expression. Error: " + e.getMessage());
            return new CalculationResponseDTO();
        }
    }
}
