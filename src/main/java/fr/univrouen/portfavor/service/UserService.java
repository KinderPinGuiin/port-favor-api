package fr.univrouen.portfavor.service;

import fr.univrouen.portfavor.entity.User;
import fr.univrouen.portfavor.exception.FunctionalException;

import java.util.List;

/**
 * The authentication service contains all the logic about the API authentication.
 */
public interface UserService {

    /**
     * Retrieves all the users and paginate them. If page is null then all the users are returned.
     *
     * @param  page                The page of the users to retrieve (start at 0).
     * @param  pageSize            The size of the page (amount of users returned, default : 10).
     * @return                     The users of the given page or all the users if page is null.
     * @throws FunctionalException Exception thrown if page < 0 or pageSize <= 0
     */
    List<User> getAll(Integer page, Integer pageSize) throws FunctionalException;

    /**
     * Retrieves the user associated to the given ID.
     *
     * @param  id                  The ID of the user to retrieve.
     * @return                     The asked user.
     * @throws FunctionalException Exception thrown if the given ID is invalid.
     */
    User getById(Long id) throws FunctionalException;

    /**
     * Creates the user associated to the given information and returns it.
     *
     * @param  login                The user's nickname.
     * @param  password             The user's password.
     * @return                      The user's registered.
     * @throws FunctionalException  Exception thrown if the given login already exists or if the given information are
     *                              invalid (bad email, invalid password...).
     */
    User create(String login, String password) throws FunctionalException;

}
