package fr.univrouen.portfavor.controller;

import fr.univrouen.portfavor.dto.request.authentication.AuthenticationRequestDTO;
import fr.univrouen.portfavor.dto.response.authentication.AuthenticationResponseDTO;
import fr.univrouen.portfavor.exception.FunctionalException;
import fr.univrouen.portfavor.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The authentication controller allows a user to connect to the API.
 */
@RestController
public class AuthenticationController {

    public static final String LOGIN_ENDPOINT = "/login";

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * Check the given credentials and returns a token if they are valid.
     *
     * @param  request The login request
     * @return         The authentication response DTO containing the token.
     */
    @PostMapping(LOGIN_ENDPOINT)
    @ResponseBody
    private AuthenticationResponseDTO login(@RequestBody AuthenticationRequestDTO request) throws FunctionalException {
        return new AuthenticationResponseDTO(this.authenticationService.login(request.login(), request.password()));
    }

}
