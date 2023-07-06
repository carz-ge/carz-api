package ge.carapp.carappapi.controller;

import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.schema.order.OrderInitializationResponse;
import ge.carapp.carappapi.schema.order.OrderInput;
import ge.carapp.carappapi.security.AuthenticatedUserProvider;
import ge.carapp.carappapi.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

@Controller
@RequiredArgsConstructor
@Validated
public class OrderController {
    private final OrderService orderService;

    @MutationMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public OrderInitializationResponse createOrder(@Argument OrderInput order) {
        UserEntity authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser();
        return orderService.initializeOrder(authenticatedUser, order);
    }

}
