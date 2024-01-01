package fr.univrouen.portfavor.service.standard;

import fr.univrouen.portfavor.constant.error.ErrorMessage;
import fr.univrouen.portfavor.entity.User;
import fr.univrouen.portfavor.exception.FunctionalException;
import fr.univrouen.portfavor.repository.UserRepository;
import fr.univrouen.portfavor.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service("standard-authentication-service")
public class StandardAuthenticationService implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    /**
     * The configured password encoder.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static Logger logger = LoggerFactory.getLogger(StandardAuthenticationService.class);

    @Override
    public User getCurrentUser() {
        var currentUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return currentUser instanceof User ? (User) currentUser : null;
    }

    @Override
    public User getUserByToken(String token) {
        return this.userRepository.findByToken(token).orElse(null);
    }

    @Override
    @Transactional(rollbackFor = { Exception.class })
    public User login(String login, String password) throws FunctionalException {
        // Get the user associated to the given nickname
        var user = this.userRepository
            .findByUsername(login)
            .orElseThrow(() -> new FunctionalException(ErrorMessage.INVALID_CREDENTIALS, HttpStatus.FORBIDDEN));

        // Check the user password
        if (!this.passwordEncoder.matches(password, user.getPassword())) {
            throw new FunctionalException(ErrorMessage.INVALID_CREDENTIALS, HttpStatus.FORBIDDEN);
        }

        // Creates the user's token and associate it to him
        user.setToken(UUID.randomUUID().toString());
        this.userRepository.save(user);

        // Log the connection
        logger.info(user.getUsername() + " logged in.");

        return user;
    }

    @Override
    @Transactional(rollbackFor = { Exception.class })
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository
            .findByUsername(username == null ? "" : username)
            .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
    }

}
