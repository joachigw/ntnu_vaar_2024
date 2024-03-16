package idatt2105.oving5.services;
import idatt2105.oving5.dto.AppUserDTO;
import idatt2105.oving5.model.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {
    private final Logger logger = LoggerFactory.getLogger(AppUserService.class);

    public AppUserDTO verify(AppUser appUser) {
        AppUserDTO appUserDTO = null;

        //TODO: Verify user through database (maybe use DAO somewhere?)

        return appUserDTO;
    }

}
