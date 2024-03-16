package idatt2105.oving5.controller;

import idatt2105.oving5.model.Expression;
import idatt2105.oving5.model.Calculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/calculator")
@CrossOrigin(origins = "http://localhost:5173")
public class CalculatorController {

    @PostMapping("/calculate")
    public ResponseEntity<?> calculateExpression(@RequestBody Expression expression) {

        Calculator calculator = new Calculator();
        Logger logger = LoggerFactory.getLogger(CalculatorController.class);

        try {
            logger.info("Calculating the following expression: " + expression.toString());

            double result = calculator.calculate(expression);

            logger.info("Sending the following result as response: {'result': " + result + "}");

            Map<String, Double> response = Collections.singletonMap("result", result);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("There was an error with the calculation of the expression. Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
