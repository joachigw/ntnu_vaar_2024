package idatt2105.oving5.repository;

import idatt2105.oving5.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AppUserRepository
        extends JpaRepository<AppUser, Integer> {

    AppUser findByUsername(String username);
    AppUser findByPassword(String password);
}
