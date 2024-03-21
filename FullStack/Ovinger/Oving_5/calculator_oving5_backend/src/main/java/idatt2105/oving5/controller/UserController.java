package idatt2105.oving5.controller;

import idatt2105.oving5.model.User;
import idatt2105.oving5.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/")
    public List<User> getAppUsers() {
        return userService.findAllAppUsers();
    }
}
