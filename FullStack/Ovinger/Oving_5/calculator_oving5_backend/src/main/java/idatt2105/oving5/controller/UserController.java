package idatt2105.oving5.controller;

import idatt2105.oving5.model.Role;
import idatt2105.oving5.model.User;
import idatt2105.oving5.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("/")
//    public String userHello() {
//        return "User level access";
//    }

    @GetMapping("/")
    public List<User> getAppUsers() {
        return userService.findAllAppUsers();
    }

    @PostMapping("/")
    public Boolean addAppUser(@RequestBody User appUser) {
        logger.info("Adding new user: " + appUser);
        userService.addAppUser(appUser);
        return true;
    }

    @PostMapping("/login")
    public Boolean loginUser(@RequestBody User user) {
        logger.info("Verifying user: " + user);
        return userService.verifyAppUser(user);
    }
}
