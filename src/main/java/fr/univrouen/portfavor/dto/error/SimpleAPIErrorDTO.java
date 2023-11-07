package fr.univrouen.portfavor.dto.error;

/**
 * A simple API error only containing an error message.
 */
public record SimpleAPIErrorDTO(
    String message
) implements APIErrorDTO {}

