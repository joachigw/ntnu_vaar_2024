package idatt2105.oving5.controller;

import idatt2105.oving5.model.AppUser;
import idatt2105.oving5.services.AppUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appusers")
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/")
    public List<AppUser> getAppUsers() {
        return appUserService.findAllAppUsers();
    }

    @PostMapping("/")
    public void addAppUser(@RequestBody AppUser appUser) {
        appUserService.addAppUser(appUser);
    }
}
