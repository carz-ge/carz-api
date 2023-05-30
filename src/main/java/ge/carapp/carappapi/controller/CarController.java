package ge.carapp.carappapi.controller;

import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.schema.CarSchema;
import ge.carapp.carappapi.schema.graphql.CarInput;
import ge.carapp.carappapi.security.AuthenticatedUserProvider;
import ge.carapp.carappapi.service.CarService;
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
public class CarController {
    private final CarService carService;

    @QueryMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public List<CarSchema> listCars() {
        UserEntity authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser();
        return carService.getCars(authenticatedUser);
    }

    @MutationMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public CarSchema addCar(@Argument CarInput carInput) {
        UserEntity authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser();
        return carService.addCar(authenticatedUser, carInput);
    }

    @MutationMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public boolean removeCar(@Argument UUID carId) {
        UserEntity authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser();
        return carService.removeCar(authenticatedUser, carId);
    }

    @MutationMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public CarSchema updateCar( @Argument UUID carId, @Argument CarInput carInput) {
        UserEntity authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser();
        return carService.updateCar(authenticatedUser, carId, carInput);
    }
}
