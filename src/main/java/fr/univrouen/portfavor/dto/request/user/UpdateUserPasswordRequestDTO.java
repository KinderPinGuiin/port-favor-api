package fr.univrouen.portfavor.dto.request.user;

import lombok.*;

/**
 * Request sent to update a user password.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UpdateUserPasswordRequestDTO {

    /**
     * The user's old (current) password.
     */
    private @NonNull String oldPassword;

    /**
     * The user's new password.
     */
    private @NonNull String newPassword;

}
