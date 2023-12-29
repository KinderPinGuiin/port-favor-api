package fr.univrouen.portfavor.dto.response.authentication;

import fr.univrouen.portfavor.dto.response.role.RoleResponseDTO;
import lombok.*;

import java.util.Set;

/**
 * Represents the response sent to a authenticated user.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDTO {

    /**
     * The user's ID.
     */
    private Long id;

    /**
     * The user's username.
     */
    private String username;

    /**
     * The user's roles.
     */
    private Set<RoleResponseDTO> roles;

    /**
     * The generated user's token.
     */
    private String token;

}
