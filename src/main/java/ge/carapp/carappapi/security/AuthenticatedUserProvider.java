package ge.carapp.carappapi.security;

import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.exception.NotAuthorizedException;
import ge.carapp.carappapi.schema.UserSchema;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public final class AuthenticatedUserProvider {

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static CustomUserDetails getAuthenticatedUserDetails() {
        if (!isAuthenticated())
            throw new NotAuthorizedException();
        return (CustomUserDetails) getAuthentication().getPrincipal();
    }

    public static UserEntity getAuthenticatedUser() {
        return getAuthenticatedUserDetails().user();
    }

    public static UUID getAuthenticatedUserId() {
        return getAuthenticatedUser().getId();
    }

    public static boolean isAuthenticated() {
        return getAuthentication().isAuthenticated();
    }
}
