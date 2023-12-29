package fr.univrouen.portfavor.dto.request.user;

import lombok.*;

/**
 * Request sent to update a user.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UpdateUserRequestDTO {

    /**
     * The user's new username.
     */
    private @NonNull String newUsername;

}
