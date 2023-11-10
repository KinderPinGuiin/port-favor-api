package fr.univrouen.portfavor.dto.response.authentication;

import fr.univrouen.portfavor.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Set;

/**
 * Represents the response sent to a registered user.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponseDTO {
    private @NonNull String login;
    private @NonNull String password;
    private String token;
    private Set<Role> roles;
}
