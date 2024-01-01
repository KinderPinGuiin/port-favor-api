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
    ACCESS_DENIED("Accès refusé, veuillez-vous identifier sur un compte ayant les permissions d'effectuer cette opération."),
    INVALID_PAGINATION("Page ou taille de page invalide."),

    // Authentication errors
    INVALID_CREDENTIALS("Adresse email ou mot de passe invalide."),
    USERNAME_ALREADY_USED("Adresse email déjà utilisée."),

    // User errors
    INVALID_USER_ID("Identifiant utilisateur invalide."),
    INVALID_EMAIL("Adresse mail invalide."),
    INVALID_ROLE_ID("Le rôle donné est invalide."),
    PASSWORD_IS_TOO_SHORT("Mot de passe trop court (8 caractères minimum)."),
    INVALID_OLD_PASSWORD("Ancien mot de passe invalide."),

    // Images errors
    CANT_UPLOAD_IMAGES("Impossible d'uploader l'image."),
    CANT_DETERMINE_CONTENT_TYPE("Le type de fichier ne peut pas être déterminé ou est invalide."),
    INVALID_CONTENT_TYPE("Type de fichier invalide (seules les images sont autorisées)."),
    IMAGE_NOT_FOUND("Le nom de l'image est invalide.");

    /**
     * The error message.
     */
    private final String message;

    private ErrorMessage(String message) {
        this.message = message;
    }

}
