package ge.carapp.carappapi.controller;

import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.schema.graphql.ManagersOrderResponseInput;
import ge.carapp.carappapi.security.AuthenticatedUserProvider;
import ge.carapp.carappapi.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    @MutationMapping
    public Boolean respondToBookingRequest(ManagersOrderResponseInput input) {
        UserEntity authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser();

        return bookingService.respondToBookingRequest(authenticatedUser,input);
    }

}
