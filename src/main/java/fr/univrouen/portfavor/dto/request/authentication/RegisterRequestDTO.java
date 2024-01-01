package fr.univrouen.portfavor.dto.request.authentication;

import lombok.*;

/**
 * Represents a registration request.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class RegisterRequestDTO {

    /**
     * The user's email.
     */
    private @NonNull String email;

    /**
     * The user's password.
     */
    private @NonNull String password;

}
