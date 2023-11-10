package fr.univrouen.portfavor.dto.response.authentication;

import fr.univrouen.portfavor.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Set;

/**
 * Represents the response sent to a authenticated user.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDTO {
    private String token;
}
