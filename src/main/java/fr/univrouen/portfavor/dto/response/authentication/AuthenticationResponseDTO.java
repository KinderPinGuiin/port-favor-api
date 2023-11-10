package fr.univrouen.portfavor.dto.response.authentication;

import lombok.*;

/**
 * Represents the response sent to a authenticated user.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDTO {

    /**
     * The generated user's token.
     */
    private String token;

}
