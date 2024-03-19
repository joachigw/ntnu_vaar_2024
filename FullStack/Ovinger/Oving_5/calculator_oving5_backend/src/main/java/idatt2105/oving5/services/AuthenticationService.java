package idatt2105.oving5.services;

import idatt2105.oving5.dto.LoginResponseDTO;
import idatt2105.oving5.model.Role;
import idatt2105.oving5.model.User;
import idatt2105.oving5.repository.RoleRepository;
import idatt2105.oving5.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final TokenService tokenService;

    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository,
                                 PasswordEncoder encoder, AuthenticationManager authManager,
                                 TokenService tokenService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.authManager = authManager;
        this.tokenService = tokenService;
    }

    public User registerUser(String username, String password) {
        String encodedPassword = encoder.encode(password);
        Role userRole = null;
        if (roleRepository.findByAuthority("USER").isPresent()) userRole = roleRepository.findByAuthority("USER").get();

        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        return userRepository.save(new User(username, encodedPassword, authorities));
    }

    public LoginResponseDTO loginUser(String username, String password) {
        try {
            Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            String token = tokenService.generateJwt(auth);

            Optional<User> optionalUser = userRepository.findByUsername(username);

            if (optionalUser.isPresent()) {
                return new LoginResponseDTO(optionalUser.get(), token);
            }

        } catch (AuthenticationException e) {
            // TODO: Return proper exception
            return new LoginResponseDTO(null, "");
        }

        return null;
    }
}
