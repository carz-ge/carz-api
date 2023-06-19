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
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public UserSchema updateUser(@Argument UpdateUserInput input) {
        UserEntity authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser();
        return userService.updateUser(authenticatedUser, input);
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Boolean addDeviceToken(@Argument AddDeviceTokenInput input) {
        UserEntity authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser();
        userService.addDeviceToken(authenticatedUser, input);
        return true;
    }
}
