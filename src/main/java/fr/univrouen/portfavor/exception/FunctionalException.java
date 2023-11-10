package fr.univrouen.portfavor.exception;

import fr.univrouen.portfavor.constant.error.ErrorMessage;
import fr.univrouen.portfavor.dto.error.APIErrorDTO;
import fr.univrouen.portfavor.dto.error.SimpleAPIErrorDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * A functional exception is an exception thrown inside the services. They contain a DTO that will automatically be sent
 * to the user thanks to the exception handler.
 */
@Getter
@Setter
public class FunctionalException extends Exception {

    /**
     * The error DTO associated to the error.
     */
    private APIErrorDTO error;

    /**
     * The HTTP status associated to the error.
     */
    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    /**
     * FunctionalException constructor taking an APIErrorDTO as an error and an http status.
     *
     * @param error      The error DTO.
     * @param httpStatus The http status associated to the error.
     */
    public FunctionalException(APIErrorDTO error, HttpStatus httpStatus) {
        super(error.getMessage());
        this.error = error;
        this.httpStatus = httpStatus;
    }

    /**
     * FunctionalException constructor taking an APIErrorDTO as an error.
     *
     * @param error The error DTO.
     */
    public FunctionalException(APIErrorDTO error) {
        super(error.getMessage());
        this.error = error;
    }

    /**
     * FunctionalException constructor taking a string as message.
     *
     * @param message The exception message.
     */
    public FunctionalException(String message) {
        this(new SimpleAPIErrorDTO(message));
    }

    /**
     * FunctionalException constructor taking a string as message and the http status associated to the error.
     *
     * @param message    The exception message.
     * @param httpStatus The error's http status.
     */
    public FunctionalException(String message, HttpStatus httpStatus) {
        this(new SimpleAPIErrorDTO(message), httpStatus);
    }

    /**
     * FunctionalException constructor taking an ErrorMessage and the http status associated to the error.
     *
     * @param message    The exception message as an ErrorMessage type.
     * @param httpStatus The error's http status.
     */
    public FunctionalException(ErrorMessage message, HttpStatus httpStatus) {
        this(new SimpleAPIErrorDTO(message.getMessage()), httpStatus);
    }

}
