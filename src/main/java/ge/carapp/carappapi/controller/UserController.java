package ge.carapp.carappapi.controller;

import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.schema.UserSchema;
import ge.carapp.carappapi.schema.graphql.AddDeviceTokenInput;
import ge.carapp.carappapi.schema.graphql.UpdateUserInput;
import ge.carapp.carappapi.security.AuthenticatedUserProvider;
import ge.carapp.carappapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public UserSchema getMe() {
        return UserSchema.convert(AuthenticatedUserProvider.getAuthenticatedUser());
    }

    @QueryMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public UserSchema getUserById(@Argument UUID userId) {
        return userService.getUserSchemaById(userId).orElse(null);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public UserSchema updateUser(@Validated @Argument UpdateUserInput input) {
        UserEntity authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser();
        return userService.updateUser(authenticatedUser, input);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Boolean addDeviceToken(@Argument AddDeviceTokenInput input) {
        UserEntity authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser();
        userService.addDeviceToken(authenticatedUser, input);
        return true;
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Boolean removeUser() {
        UserEntity authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser();
        userService.removeUser(authenticatedUser);
        return true;
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Boolean deactivateUser() {
        UserEntity authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser();
        userService.deactivateUser(authenticatedUser);
        return true;
    }
}
