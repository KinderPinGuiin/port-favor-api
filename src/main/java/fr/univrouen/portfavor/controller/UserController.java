package fr.univrouen.portfavor.controller;

import fr.univrouen.portfavor.constant.role.RoleID;
import fr.univrouen.portfavor.dto.response.user.UserResponseDTO;
import fr.univrouen.portfavor.exception.FunctionalException;
import fr.univrouen.portfavor.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    /*
     * Routes
     */

    public static final String USER_ROOT = "/user";
    public static final String GET_USERS = USER_ROOT + "/get-all";
    public static final String GET_USER = USER_ROOT + "/get/{id}";
    public static final String CREATE_USER = USER_ROOT + "/create";
    public static final String UPDATE_USER = USER_ROOT + "/update";
    public static final String DELETE_USER = USER_ROOT + "/delete";

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Get the user associated to the given ID and returns it.
     *
     * @param  id The user's ID.
     * @return    The asked user.
     */
    @GetMapping(GET_USER)
    @PreAuthorize("hasAuthority('" + RoleID.ADMIN + "')")
    public UserResponseDTO getUser(@PathVariable("id") Long id) throws FunctionalException  {
        return this.modelMapper.map(this.userService.getById(id), UserResponseDTO.class);
    }

}
