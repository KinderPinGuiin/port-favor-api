package fr.univrouen.portfavor.dto.request.authentication;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Represents a login request.
 */
@RequiredArgsConstructor
@Accessors(fluent = true) @Getter
public class AuthenticationRequestDTO {

    /**
     * User login (username).
     */
    private final String login;

    /**
     * User password.
     */
    private final String password;

}
