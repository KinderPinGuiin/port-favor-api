package fr.univrouen.portfavor.dto.response.authentication;

import fr.univrouen.portfavor.dto.response.role.RoleDTO;
import fr.univrouen.portfavor.entity.Role;
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
     * The created user's login.
     */
    private @NonNull String username;

    /**
     * The created user's token.
     */
    private String token;

    /**
     * The created user's roles.
     */
    private Set<RoleDTO> roles;

}
