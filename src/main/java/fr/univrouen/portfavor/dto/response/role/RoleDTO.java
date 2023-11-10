package fr.univrouen.portfavor.dto.response.role;

import lombok.*;

/**
 * DTO representing a user role.
 */
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class RoleDTO {

    /**
     * The role's name.
     */
    private @NonNull String name;

}
