package idatt2105.oving5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.SpringVersion;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@CrossOrigin(origins = "http://localhost:5173")
public class CalculatorOving5Application {

    public static void main(String[] args) {
        SpringApplication.run(CalculatorOving5Application.class, args);
    }

}
