package fr.univrouen.portfavor.dto.request.user;

import lombok.*;

/**
 * Request sent to delete a user.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class DeleteUserRequestDTO {

    private @NonNull Long id;

}
