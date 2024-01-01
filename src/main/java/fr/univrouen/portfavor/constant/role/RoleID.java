package fr.univrouen.portfavor.constant.role;

import java.util.List;

/**
 * Contains all the API role IDs.
 */
public class RoleID {

    // Contains all the roles to iterate on them
    // DON'T FORGET TO UPDATE IT IF THE ROLES BELOW ARE UPDATED
    public static final List<String> ALL = List.of(RoleID.USER, RoleID.PRIVATE_USER, RoleID.ADMIN);

    // Default user
    public static final String USER = "USER";

    // User that has the access to the private portfolio
    public static final String PRIVATE_USER = "PRIVATE_USER";

    // Admin user
    public static final String ADMIN = "ADMIN";

}
