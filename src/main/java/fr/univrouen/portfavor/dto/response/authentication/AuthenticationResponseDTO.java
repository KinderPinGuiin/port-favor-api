package fr.univrouen.portfavor.dto.response.authentication;

/**
 * Represents the response sent to an authenticated user. It contains the token to use to make requests to the API.
 *
 * @param token User authentication token.
 */
public record AuthenticationResponseDTO(
    String token
) {}
