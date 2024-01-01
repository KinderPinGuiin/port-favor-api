package fr.univrouen.portfavor.dto.response.authentication;

import fr.univrouen.portfavor.dto.response.role.RoleResponseDTO;
import lombok.*;

import java.util.Set;

/**
 * Represents the response sent to a registered user.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class RegisterResponseDTO {

    /**
     * The created user's email.
     */
    private @NonNull String email;

    /**
     * The created user's token.
     */
    private String token;

    /**
     * The created user's roles.
     */
    private Set<RoleResponseDTO> roles;

}
