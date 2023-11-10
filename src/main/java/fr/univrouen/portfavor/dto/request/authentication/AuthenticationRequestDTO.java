package fr.univrouen.portfavor.dto.request.authentication;

import lombok.*;

/**
 * Represents a authentication request.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class AuthenticationRequestDTO {

    /**
     * The user's login.
     */
    private @NonNull String login;

    /**
     * The user's password.
     */
    private @NonNull String password;

}
