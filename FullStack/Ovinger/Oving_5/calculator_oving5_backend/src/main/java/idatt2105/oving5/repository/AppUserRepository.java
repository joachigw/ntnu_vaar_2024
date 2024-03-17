package idatt2105.oving5.repository;

import idatt2105.oving5.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository
        extends JpaRepository<AppUser, Integer> {
}
