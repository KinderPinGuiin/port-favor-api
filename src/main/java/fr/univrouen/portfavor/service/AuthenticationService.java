package fr.univrouen.portfavor.service;

import fr.univrouen.portfavor.entity.User;
import fr.univrouen.portfavor.exception.FunctionalException;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * The authentication service contains all the logic about the API authentication.
 */
public interface AuthenticationService extends UserDetailsService {

    /**
     * @return The current authenticated user.
     */
    User getCurrentUser();

    /**
     * Get the user associated to the given token and returns it. If the token is invalid then null is returned.
     *
     * @param  token The user token.
     * @return       The user associated to the given token.
     */
    User getUserByToken(String token);

    /**
     * Login the user associated to the given login information and returns the user's token.
     *
     * @param  login                The user's nickname.
     * @param  password             The user's password.
     * @return                      The user's generated token.
     * @throws FunctionalException  Exception thrown if the given credentials are invalid.
     */
    String login(String login, String password) throws FunctionalException;

}
