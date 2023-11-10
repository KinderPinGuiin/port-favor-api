package fr.univrouen.portfavor.dto.request.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Represents a authentication request.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequestDTO {

    private @NonNull String login;
    private @NonNull String password;
}
