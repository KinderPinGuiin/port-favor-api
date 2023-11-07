package fr.univrouen.portfavor.dto.response.authentication;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Represents the response sent to an authenticated user. It contains the token to use to make requests to the API.
 */
@RequiredArgsConstructor
@Accessors(fluent = true) @Getter
public class AuthenticationResponseDTO {

    /**
     * User authentication token.
     */
    private final String token;

}
