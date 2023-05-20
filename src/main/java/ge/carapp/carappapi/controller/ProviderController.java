package ge.carapp.carappapi.controller;

import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.schema.CategorySchema;
import ge.carapp.carappapi.schema.ProviderSchema;
import ge.carapp.carappapi.schema.graphql.ProviderInput;
import ge.carapp.carappapi.security.AuthenticatedUserProvider;
import ge.carapp.carappapi.service.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ProviderController {
    private final ProviderService providerService;

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public List<ProviderSchema> listProviders() {
        return providerService.getProviders();
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ProviderSchema createProvider(@Argument ProviderInput input) {
        UserEntity authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser();
        return providerService.createProvider(authenticatedUser, input);
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ProviderSchema updateProvider(@Argument UUID categoryId, @Argument ProviderInput input) {
        UserEntity authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser();
        return providerService.updateProvider(authenticatedUser, categoryId, input);
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public boolean removeProvider(@Argument UUID providerId) {
        UserEntity authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser();
        return providerService.removeProvider(authenticatedUser, providerId);
    }
}
