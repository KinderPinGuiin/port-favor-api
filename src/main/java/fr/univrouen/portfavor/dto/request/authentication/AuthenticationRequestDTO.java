package fr.univrouen.portfavor.dto.request.authentication;

/**
 * Represents a login request.
 *
 * @param login    User login (username).
 * @param password User password.
 */
public record AuthenticationRequestDTO(
    String login,
    String password
) {}
