package fr.univrouen.portfavor.constant.error;

import lombok.Getter;

/**
 * Enumeration containing all the application error messages.
 */
@Getter
public enum ErrorMessage {

    // Generic errors
    INTERNAL_SERVER_ERROR("Une erreur interne est survenue."),
    UNKNOWN_ERROR("Une erreur non identifiée est survenue."),
    ACCESS_DENIED("Accès refusé, veuillez-vous identifier."),

    // Authentication errors
    INVALID_CREDENTIALS("Nom d'utilisateur ou mot de passe invalide."),
    INVALID_USERNAME("Nom d'utilisateur invalide."),
    USERNAME_ALREADY_USED("Nom d'utilisateur déjà utilisé");

    /**
     * The error message.
     */
    private final String message;

    private ErrorMessage(String message) {
        this.message = message;
    }

}
