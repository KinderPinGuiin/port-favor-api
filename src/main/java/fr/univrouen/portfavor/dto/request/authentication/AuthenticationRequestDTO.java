package fr.univrouen.portfavor.dto.request.authentication;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.jackson.Jacksonized;

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
