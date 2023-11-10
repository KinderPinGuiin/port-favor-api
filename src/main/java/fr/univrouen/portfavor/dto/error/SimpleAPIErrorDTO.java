package fr.univrouen.portfavor.dto.error;

import lombok.*;

/**
 * A simple API error only containing an error message.
 */
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class SimpleAPIErrorDTO implements APIErrorDTO {

    /**
     * The error message.
     */
    private @NonNull String message;

}

