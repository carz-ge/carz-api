package ge.carapp.carappapi.controller;

import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.schema.CommissionSchema;
import ge.carapp.carappapi.schema.order.OrderSchema;
import ge.carapp.carappapi.schema.order.OrderInitializationResponse;
import ge.carapp.carappapi.schema.order.OrderInput;
import ge.carapp.carappapi.security.AuthenticatedUserProvider;
import ge.carapp.carappapi.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

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


    @QueryMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public CommissionSchema getCommission(@Argument UUID productId, @Argument UUID packageId) {
        UserEntity authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser();
        return orderService.calculateCommission(authenticatedUser, productId, packageId);
    }

    @QueryMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public List<OrderSchema> listOrders() {
        UserEntity authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser();
        return orderService.listUserOrders(authenticatedUser);
    }

    @QueryMapping
    @PreAuthorize("hasAnyAuthority('ROLE_MANAGER', 'ROLE_ADMIN')")
    public List<OrderSchema> listOrdersByManager() {
        UserEntity authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser();
        return orderService.listProviderOrders(authenticatedUser);
    }
}
