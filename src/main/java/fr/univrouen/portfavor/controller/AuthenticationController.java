package fr.univrouen.portfavor.controller;

import fr.univrouen.portfavor.dto.request.authentication.AuthenticationRequestDTO;
import fr.univrouen.portfavor.dto.request.authentication.RegisterRequestDTO;
import fr.univrouen.portfavor.dto.response.authentication.AuthenticationResponseDTO;
import fr.univrouen.portfavor.dto.response.authentication.RegisterResponseDTO;
import fr.univrouen.portfavor.exception.FunctionalException;
import fr.univrouen.portfavor.service.AuthenticationService;
import fr.univrouen.portfavor.service.UserService;
import org.modelmapper.ModelMapper;
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
    public static final String REGISTER_ENDPOINT = "/register";

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Check the given credentials and returns a token if they are valid.
     *
     * @param  request The login request
     * @return         The authentication response DTO containing the token.
     */
    @PostMapping(LOGIN_ENDPOINT)
    @ResponseBody
    private AuthenticationResponseDTO login(@RequestBody AuthenticationRequestDTO request) throws FunctionalException {
        return new AuthenticationResponseDTO(this.authenticationService.login(request.getLogin(), request.getPassword()));
    }

    /**
     * TODO
     *
     * @param request
     * @return
     * @throws FunctionalException
     */
    @PostMapping(REGISTER_ENDPOINT)
    @ResponseBody
    private RegisterResponseDTO register(@RequestBody RegisterRequestDTO request) throws FunctionalException {
        return modelMapper.map(this.userService.create(request.getLogin(), request.getPassword()), RegisterResponseDTO.class);
    }

}
