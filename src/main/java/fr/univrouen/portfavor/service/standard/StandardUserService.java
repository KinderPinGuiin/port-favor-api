package fr.univrouen.portfavor.service.standard;

import fr.univrouen.portfavor.constant.error.ErrorMessage;
import fr.univrouen.portfavor.constant.role.RoleID;
import fr.univrouen.portfavor.entity.User;
import fr.univrouen.portfavor.exception.FunctionalException;
import fr.univrouen.portfavor.repository.RoleRepository;
import fr.univrouen.portfavor.repository.UserRepository;
import fr.univrouen.portfavor.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service("standard-user-service")
public class StandardUserService implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static Logger logger = LoggerFactory.getLogger(StandardAuthenticationService.class);

    @Override
    public User getById(Long id) throws FunctionalException {
        // Retrieve the user and check it
        var user = this.userRepository.findById(id);
        if (user.isEmpty()) {
            throw new FunctionalException(ErrorMessage.INVALID_ID, HttpStatus.NOT_FOUND);
        }

        return user.get();
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public User create(String login, String password) throws FunctionalException {
        // Check if the given parameters are valid
        if (!login.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
            throw new FunctionalException(ErrorMessage.INVALID_EMAIL, HttpStatus.BAD_REQUEST);
        }
        if (password.length() < 8) {
            throw new FunctionalException(ErrorMessage.PASSWORD_IS_TOO_SHORT, HttpStatus.BAD_REQUEST);
        }

        // Get the user associated to the given nickname
        if (this.userRepository.findByUsername(login).orElse(null) != null) {
            throw new FunctionalException(ErrorMessage.USERNAME_ALREADY_USED, HttpStatus.BAD_REQUEST);
        }

        // Creates the user in the database
        var user = this.userRepository.save(new User(
            0L,
            login,
            this.passwordEncoder.encode(password),
            UUID.randomUUID().toString(),
            new HashSet<>(List.of(this.roleRepository.findById(RoleID.USER).get()))
        ));

        // Log the connection
        logger.info("User " + user.getUsername() + " created with ID " + user.getId() + ".");

        return user;
    }

}
