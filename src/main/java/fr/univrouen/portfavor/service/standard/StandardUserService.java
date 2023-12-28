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
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public List<User> getAll(Integer page, Integer pageSize) throws FunctionalException {
        // If page is null returns all the users
        if (page == null) {
            return this.userRepository.findAll();
        }

        // Check params
        if (page < 0 || (pageSize != null && pageSize <= 0)) {
            throw new FunctionalException(ErrorMessage.INVALID_PAGINATION, HttpStatus.BAD_REQUEST);
        }

        // Apply pagination
        return this.userRepository.findAll(PageRequest.of(page, pageSize == null ? 10 : pageSize)).toList();
    }

    @Override
    public User getById(Long id) throws FunctionalException {
        // Retrieve the user and check it
        var user = this.userRepository.findById(id);
        if (user.isEmpty()) {
            throw new FunctionalException(ErrorMessage.INVALID_USER_ID, HttpStatus.NOT_FOUND);
        }

        return user.get();
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public User create(String login, String password, Set<String> roles) throws FunctionalException {
        // Check if the given parameters are valid
        this.checkUserInformation(login, password, roles);

        // Get the user associated to the given login and check that it doesn't already exist
        if (this.userRepository.findByUsername(login).orElse(null) != null) {
            throw new FunctionalException(ErrorMessage.USERNAME_ALREADY_USED, HttpStatus.BAD_REQUEST);
        }

        // Creates the user in the database
        var user = this.userRepository.save(new User(
            0L,
            login,
            this.passwordEncoder.encode(password),
            UUID.randomUUID().toString(),
            roles.isEmpty()
                ? new HashSet<>(List.of(this.roleRepository.findById(RoleID.USER).get()))
                : roles.stream().map(role -> this.roleRepository.findById(role).get()).collect(Collectors.toSet())
        ));

        // Log the connection
        logger.info("User " + user.getUsername() + " created with ID " + user.getId() + ".");

        return user;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public User update(Long id, String login, String password, Set<String> roles) throws FunctionalException {
        // Get the user associated to the given ID
        var user = this.getById(id);

        // Check if the given parameters are valid
        this.checkUserInformation(login, password == null ? "00000000" : password, roles);

        // Get the user associated to the given login and check that it doesn't already exist
        if (!user.getUsername().equals(login) && this.userRepository.findByUsername(login).orElse(null) != null) {
            throw new FunctionalException(ErrorMessage.USERNAME_ALREADY_USED, HttpStatus.BAD_REQUEST);
        }

        // Update the user's information
        user.setUsername(login);
        if (password != null && !this.passwordEncoder.matches(password, user.getPassword())) {
            // If the password is not the same then we refresh the token
            user.setPassword(this.passwordEncoder.encode(password));
            user.setToken(UUID.randomUUID().toString());
        }
        user.setRoles(
            roles.isEmpty()
                ? new HashSet<>(List.of(this.roleRepository.findById(RoleID.USER).get()))
                : roles.stream().map(role -> this.roleRepository.findById(role).get()).collect(Collectors.toSet())
        );

        return this.userRepository.save(user);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public String updatePassword(User user, String oldPassword, String newPassword) throws FunctionalException {
        // Check the given old password
        if (!this.passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new FunctionalException(ErrorMessage.INVALID_OLD_PASSWORD, HttpStatus.BAD_REQUEST);
        }

        // Checks the new password
        if (newPassword.length() < 8) {
            throw new FunctionalException(ErrorMessage.PASSWORD_IS_TOO_SHORT, HttpStatus.BAD_REQUEST);
        }

        // Updates the user's password and token
        var token = UUID.randomUUID().toString();
        user.setPassword(this.passwordEncoder.encode(newPassword));
        user.setToken(token);

        return this.userRepository.save(user).getToken();
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public User delete(Long id) throws FunctionalException {
        // Get the user and delete it
        var user = this.getById(id);
        this.userRepository.delete(user);

        return user;
    }

    /**
     * Checks the given user information and throw an exception if they are invalid.
     *
     * @param login    The user's login.
     * @param password The user's password.
     * @param roles    The user's roles ID.
     */
    private void checkUserInformation(String login, String password, Set<String> roles) throws FunctionalException {
        // Check if the given parameters are valid
        if (!login.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
            throw new FunctionalException(ErrorMessage.INVALID_EMAIL, HttpStatus.BAD_REQUEST);
        }
        if (password.length() < 8) {
            throw new FunctionalException(ErrorMessage.PASSWORD_IS_TOO_SHORT, HttpStatus.BAD_REQUEST);
        }
        for (String roleID : roles) {
            this.roleRepository.findById(roleID)
                .orElseThrow(() -> new FunctionalException(ErrorMessage.INVALID_ROLE_ID, HttpStatus.NOT_FOUND));
        }
    }

}
