package idatt2105.oving5.controller;

import idatt2105.oving5.model.AppUser;
import idatt2105.oving5.services.AppUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appusers")
@CrossOrigin(origins = "http://localhost:5173")
public class AppUserController {

    private final Logger logger = LoggerFactory.getLogger(AppUserController.class);
    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/")
    public List<AppUser> getAppUsers() {
        logger.info("Sending all users...");
        return appUserService.findAllAppUsers();
    }

    @PostMapping("/")
    public Boolean addAppUser(@RequestBody AppUser appUser) {
        logger.info("Adding new user: " + appUser);
        appUserService.addAppUser(appUser);
        return true;
    }

    @GetMapping("/login")
    public Boolean loginUser(@RequestBody AppUser appUser) {
        logger.info("Verifying user...");
        return appUserService.verifyUser(appUser);
    }
}
