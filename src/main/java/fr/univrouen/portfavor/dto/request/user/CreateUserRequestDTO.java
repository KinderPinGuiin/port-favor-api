package fr.univrouen.portfavor.dto.request.user;

import lombok.*;

import java.util.Set;

/**
 * Request sent to create a user.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreateUserRequestDTO {

    /**
     * The user's login.
     */
    private @NonNull String login;

    /**
     * The user's password.
     */
    private @NonNull String password;

    /**
     * The user's roles (can be empty).
     */
    private @NonNull Set<String> roles;

}
