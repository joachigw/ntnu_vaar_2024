package idatt2105.oving5.controller;

import idatt2105.oving5.repository.AppUserRepository;
import idatt2105.oving5.model.AppUser;
import idatt2105.oving5.dto.AppUserDTO;
import idatt2105.oving5.services.AppUserService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/appuser")
@CrossOrigin(origins = "http://localhost:5173")
public class AppUserController {

    private AppUserRepository appUserRepository;

    public AppUserController(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @PostMapping("/login")
    public AppUserDTO loginUser(@RequestBody AppUser appUser) {
        Logger logger = LoggerFactory.getLogger(AppUserController.class);
        AppUserService appUserService = new AppUserService();

        // Verify username and password with database
        try {
            logger.info("Attempting to log in the following user: " + appUser.toString() + "...");
            return appUserService.verify(appUser);
        } catch (Exception e) {
            logger.error("Unexpected error occurred. Error: " + e.getMessage());
            return null;
        }
    }

    @GetMapping("/appusers")
    public List<AppUser> getAppUsers() {
        return appUserRepository.findAll();
    }
}
