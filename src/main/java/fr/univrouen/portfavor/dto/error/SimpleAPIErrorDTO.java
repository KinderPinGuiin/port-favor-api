package fr.univrouen.portfavor.dto.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * A simple API error only containing an error message.
 */
@RequiredArgsConstructor
@Accessors(fluent = true) @Getter
public class SimpleAPIErrorDTO implements APIErrorDTO {

    private final String message;

}

