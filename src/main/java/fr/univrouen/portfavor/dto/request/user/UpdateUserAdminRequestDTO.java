package fr.univrouen.portfavor.dto.request.user;

import lombok.*;

import java.util.Set;

/**
 * Request sent to update a user as an admin.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UpdateUserAdminRequestDTO {

    /**
     * The user's ID.
     */
    private @NonNull Long id;

    /**
     * The user's new email.
     */
    private @NonNull String email;

    /**
     * The user's new password (null if no changes).
     */
    private String password;

    /**
     * The user's new roles
     */
    private @NonNull Set<String> roles;

}
