package fr.univrouen.portfavor.service;

import fr.univrouen.portfavor.entity.User;
import fr.univrouen.portfavor.exception.FunctionalException;

/**
 * The authentication service contains all the logic about the API authentication.
 */
public interface UserService {

    /**
     * Create the user associated to the given login information and returns it.
     *
     * @param  login                The user's nickname.
     * @param  password             The user's password.
     * @return                      The user's registered.
     * @throws FunctionalException  Exception thrown if the given credentials are invalid.
     */
    User create(String login, String password) throws FunctionalException;

}
