package idatt2105.oving5.services;
import idatt2105.oving5.model.AppUser;
import idatt2105.oving5.repository.AppUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;

    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public List<AppUser> findAllAppUsers() {
        return appUserRepository.findAll();
    }

    public void addAppUser(@RequestBody AppUser appUser) {
        appUserRepository.save(appUser);
    }

    public Boolean verifyUser(AppUser appUser) {
        return appUserRepository.findByUsername(appUser.getUsername()) != null &&
                appUserRepository.findByPassword(appUser.getPassword()) != null;
    }
}
