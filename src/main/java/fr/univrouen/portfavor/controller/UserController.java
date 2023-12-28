package fr.univrouen.portfavor.controller;

import fr.univrouen.portfavor.constant.role.RoleID;
import fr.univrouen.portfavor.dto.request.user.CreateUserRequestDTO;
import fr.univrouen.portfavor.dto.request.user.UpdateUserRequestDTO;
import fr.univrouen.portfavor.dto.response.user.UserResponseDTO;
import fr.univrouen.portfavor.exception.FunctionalException;
import fr.univrouen.portfavor.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public static final String UPDATE_USER_PASSWORD = USER_ROOT + "/update-password";
    public static final String DELETE_USER = USER_ROOT + "/delete";

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Retrieves all the users and paginate them.
     *
     * @param  page     The page of the pagination (if null all users are returned)
     * @param  pageSize The page size (default: 10).
     * @return          The paginated users (or all if page is null).
     */
    @GetMapping(GET_USERS)
    @PreAuthorize("hasAuthority('" + RoleID.ADMIN + "')")
    @ResponseBody
    public List<UserResponseDTO> getAllUsers(
        @RequestParam(value = "page", required = false) Integer page,
        @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) throws FunctionalException {
        return this.userService
            .getAll(page, pageSize)
            .stream()
            .map(user -> this.modelMapper.map(user, UserResponseDTO.class))
            .toList();
    }

    /**
     * Get the user associated to the given ID and returns it.
     *
     * @param  id The user's ID.
     * @return    The asked user.
     */
    @GetMapping(GET_USER)
    @PreAuthorize("hasAuthority('" + RoleID.ADMIN + "')")
    @ResponseBody
    public UserResponseDTO getUser(@PathVariable("id") Long id) throws FunctionalException  {
        return this.modelMapper.map(this.userService.getById(id), UserResponseDTO.class);
    }

    /**
     * Creates the given user and returns it.
     *
     * @param createUserRequest The user creation request body.
     * @return                  The created user.
     */
    @PostMapping(CREATE_USER)
    @PreAuthorize("hasAuthority('" + RoleID.ADMIN + "')")
    @ResponseBody
    public UserResponseDTO createUser(@RequestBody CreateUserRequestDTO createUserRequest) throws FunctionalException {
        return this.modelMapper.map(
            this.userService.create(
                createUserRequest.getLogin(),
                createUserRequest.getPassword(),
                createUserRequest.getRoles()
            ),
            UserResponseDTO.class
        );
    }

    /**
     * Updates the given user with the given information.
     *
     * @param  updateRequest User's information.
     * @return               The updated user.
     */
    @PostMapping(UPDATE_USER)
    @PreAuthorize("hasAuthority('" + RoleID.ADMIN + "')")
    @ResponseBody
    public UserResponseDTO updateUser(@RequestBody UpdateUserRequestDTO updateRequest) throws FunctionalException {
        return this.modelMapper.map(
            this.userService.update(
                updateRequest.getId(),
                updateRequest.getLogin(),
                updateRequest.getPassword(),
                updateRequest.getRoles()
            ),
            UserResponseDTO.class
        );
    }

}
