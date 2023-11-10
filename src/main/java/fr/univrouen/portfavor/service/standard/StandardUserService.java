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
    @Transactional(rollbackFor = {Exception.class})
    public User create(String login, String password) throws FunctionalException {
        // Get the user associated to the given nickname
        if (this.userRepository.findByUsername(login).orElse(null) != null) {
            throw new FunctionalException(ErrorMessage.USERNAME_ALREADY_USED, HttpStatus.FORBIDDEN);
        }

        // Creates the user in the database
        var user = new User(
            0L,
            login,
            this.passwordEncoder.encode(password),
            UUID.randomUUID().toString(),
            new HashSet<>(List.of(this.roleRepository.findById(RoleID.USER).get()))
        );
        var persistedUser = this.userRepository.save(user);

        // Log the connection
        logger.info(user.getUsername() + " logged in.");

        return persistedUser;
    }

}
