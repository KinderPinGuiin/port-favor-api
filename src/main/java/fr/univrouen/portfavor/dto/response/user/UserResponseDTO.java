package fr.univrouen.portfavor.dto.response.user;

import fr.univrouen.portfavor.dto.response.role.RoleDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * Represents a user.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

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
    private Set<RoleDTO> roles;

}
