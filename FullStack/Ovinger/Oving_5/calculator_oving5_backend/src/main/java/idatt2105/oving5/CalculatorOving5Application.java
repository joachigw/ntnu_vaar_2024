package idatt2105.oving5;

import idatt2105.oving5.model.Role;
import idatt2105.oving5.model.User;
import idatt2105.oving5.repository.RoleRepository;
import idatt2105.oving5.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@CrossOrigin(origins = "http://localhost:5173")
public class CalculatorOving5Application {

    public static void main(String[] args) {
        SpringApplication.run(CalculatorOving5Application.class, args);
    }

    @Bean
    CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            // Exit early if DB already contains the admin user
            if (roleRepository.findByAuthority("ADMIN").isPresent()) return;

            Role adminRole = roleRepository.save(new Role("ADMIN"));
            roleRepository.save(new Role("USER"));

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);

            User admin = new User(1, "admin", encoder.encode("password"), roles);
            userRepository.save(admin);
        };
    }

}
