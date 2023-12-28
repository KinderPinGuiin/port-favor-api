package fr.univrouen.portfavor.controller;

import fr.univrouen.portfavor.constant.role.RoleID;
import fr.univrouen.portfavor.dto.request.user.CreateUserRequestDTO;
import fr.univrouen.portfavor.dto.request.user.UpdateUserPasswordRequestDTO;
import fr.univrouen.portfavor.dto.request.user.UpdateUserAdminRequestDTO;
import fr.univrouen.portfavor.dto.request.user.UpdateUserRequestDTO;
import fr.univrouen.portfavor.dto.response.authentication.AuthenticationResponseDTO;
import fr.univrouen.portfavor.dto.response.user.UserResponseDTO;
import fr.univrouen.portfavor.entity.Role;
import fr.univrouen.portfavor.exception.FunctionalException;
import fr.univrouen.portfavor.service.AuthenticationService;
import fr.univrouen.portfavor.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public static final String UPDATE_USER_ADMIN = USER_ROOT + "/update-admin";
    public static final String UPDATE_USER_PASSWORD = USER_ROOT + "/update-password";
    public static final String DELETE_USER = USER_ROOT + "/delete";

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

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
     * Updates the given user with the given information. This endpoint is only opened to admin.
     *
     * @param  updateRequest User's information.
     * @return               The updated user.
     */
    @PostMapping(UPDATE_USER_ADMIN)
    @PreAuthorize("hasAuthority('" + RoleID.ADMIN + "')")
    @ResponseBody
    public UserResponseDTO updateUserAdmin(@RequestBody UpdateUserAdminRequestDTO updateRequest) throws FunctionalException {
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

    @PostMapping(UPDATE_USER)
    @PreAuthorize("hasAuthority('" + RoleID.USER + "') || hasAuthority('" + RoleID.ADMIN + "')")
    @ResponseBody
    public UserResponseDTO updateUser(@RequestBody UpdateUserRequestDTO updateRequest) throws FunctionalException {
        var user = this.authenticationService.getCurrentUser();
        return this.modelMapper.map(
            this.userService.update(
                user.getId(),
                updateRequest.getNewUsername(),
                null,
                user.getRoles().stream().map(Role::getName).collect(Collectors.toSet())
            ),
            UserResponseDTO.class
        );
    }

    /**
     * Updates the given user's password.
     *
     * @param updateRequest The password update information.
     * @return              The updated user's new token.
     */
    @PostMapping(UPDATE_USER_PASSWORD)
    @PreAuthorize("hasAuthority('" + RoleID.USER + "') || hasAuthority('" + RoleID.ADMIN + "')")
    @ResponseBody
    public AuthenticationResponseDTO updateUserPassword(
        @RequestBody UpdateUserPasswordRequestDTO updateRequest
    ) throws FunctionalException {
        return new AuthenticationResponseDTO(
            this.userService.updatePassword(
                this.authenticationService.getCurrentUser(),
                updateRequest.getOldPassword(),
                updateRequest.getNewPassword()
            )
        );
    }

//    @DeleteMapping(DELETE_USER)
//    @PreAuthorize("hasAuthority('" + RoleID.ADMIN + "')")
//    @ResponseBody
//    public UserResponseDTO deleteUser(@RequestBody DeleteUserRequestDTO deleteUserRequest) throws FunctionalException {
//        return this.modelMapper.map(this.userService.delete(deleteUserRequest.getId()), UserResponseDTO.class);
//    }

}
