package idatt2105.oving5.controller;

import idatt2105.oving5.dto.LoginResponseDTO;
import idatt2105.oving5.dto.TokenDTO;
import idatt2105.oving5.dto.UserRegistrationDTO;
import idatt2105.oving5.model.User;
import idatt2105.oving5.services.AuthenticationService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody UserRegistrationDTO registrationDTO) {
        //TODO: Remove return of password, use DTO
        return authService.registerUser(registrationDTO.getUsername(), registrationDTO.getPassword());
    }

    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody UserRegistrationDTO registrationDTO) {
        return authService.loginUser(registrationDTO.getUsername(), registrationDTO.getPassword());
    }

    @PostMapping("/refresh")
    public TokenDTO refreshJWT(@RequestBody TokenDTO existingToken) throws InterruptedException {
        Thread.sleep(500);
        System.out.println("OLD TOKEN1: " + existingToken);
        String token = authService.refreshJWT(existingToken.getToken());
        System.out.println("OLD TOKEN2: " + existingToken);
        System.out.println("NEW TOKEN MAYBE: " + token);

        return new TokenDTO(token);
    }
}
