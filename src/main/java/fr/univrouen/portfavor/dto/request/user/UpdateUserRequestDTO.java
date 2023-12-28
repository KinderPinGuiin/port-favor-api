package fr.univrouen.portfavor.dto.request.user;

import lombok.*;

import java.util.Set;

/**
 * Request sent to update a user.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UpdateUserRequestDTO {

    /**
     * The user's ID.
     */
    private @NonNull Long id;

    /**
     * The user's new login.
     */
    private @NonNull String login;

    /**
     * The user's new password (null if no changes).
     */
    private String password;

    /**
     * The user's new roles
     */
    private @NonNull Set<String> roles;

}
