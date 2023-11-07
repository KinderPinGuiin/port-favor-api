package fr.univrouen.portfavor.exception.handler;

import fr.univrouen.portfavor.constant.error.ErrorMessage;
import fr.univrouen.portfavor.dto.error.APIErrorDTO;
import fr.univrouen.portfavor.dto.error.SimpleAPIErrorDTO;
import fr.univrouen.portfavor.exception.FunctionalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;

/**
 * This class handles all the specified exceptions that are not caught inside the application.
 */
@RestControllerAdvice
public class APIExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(APIExceptionHandler.class);

    /**
     * Handles a FunctionalException.
     *
     * @param  exception The exception that occurred.
     * @param  request   The user HTTP request.
     * @return           The response to send to the user.
     */
    @ExceptionHandler(value = { FunctionalException.class })
    private ResponseEntity<APIErrorDTO> functionalError(FunctionalException exception, WebRequest request) {
        logger.error(exception.getMessage(), exception);
        var error = exception.getError() != null
            ? exception.getError()
            : new SimpleAPIErrorDTO(ErrorMessage.UNKNOWN_ERROR.getMessage());

        return ResponseEntity
            .status(exception.getHttpStatus())
            .body(error);
    }

    /**
     * Handles the AccessDeniedException.
     *
     * @param  exception The exception that occurred.
     * @param  request   The user HTTP request.
     * @return           The response to send to the user.
     */
    @ExceptionHandler(value = { AccessDeniedException.class })
    private ResponseEntity<APIErrorDTO> accessDeniedException(AccessDeniedException exception, WebRequest request) {
        logger.error(exception.getMessage(), exception);
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(new SimpleAPIErrorDTO(ErrorMessage.ACCESS_DENIED.getMessage()));
    }

    /**
     * Handles the other exceptions.
     *
     * @param  exception The exception that occurred.
     * @return           The response to send to the user.
     */
    @ExceptionHandler(value = { Exception.class })
    private ResponseEntity<APIErrorDTO> defaultException(Exception exception, WebRequest request) {
        logger.error(exception.getMessage(), exception);
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new SimpleAPIErrorDTO(ErrorMessage.INTERNAL_SERVER_ERROR.getMessage()));
    }

}
