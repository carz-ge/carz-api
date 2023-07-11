package ge.carapp.carappapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ge.carapp.carappapi.controller.ws.manager.ManagersOrderWebSocketHandler;
import ge.carapp.carappapi.controller.ws.user.UserOrderWebSocketHandler;
import ge.carapp.carappapi.entity.BookingEntity;
import ge.carapp.carappapi.entity.ManagerEntity;
import ge.carapp.carappapi.entity.OrderEntity;
import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.exception.GeneralException;
import ge.carapp.carappapi.repository.BookingRepository;
import ge.carapp.carappapi.repository.OrderRepository;
import ge.carapp.carappapi.schema.BookingStatus;
import ge.carapp.carappapi.schema.graphql.ManagersOrderResponseInput;
import ge.carapp.carappapi.schema.order.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final ObjectMapper objectMapper;
    private final BookingRepository bookingRepository;
    private final ProviderService providerService;
    private final ManagerService managerService;
    private final UserOrderWebSocketHandler userOrderWebSocketHandler;
    private final ManagersOrderWebSocketHandler managersOrderWebSocketHandler;
    private final OrderRepository orderRepository;


    public OrderEntity initializeBookingNotifications(OrderEntity orderEntity) {
        try {

            userOrderWebSocketHandler.sendMessage(orderEntity.getUser(), objectMapper.writeValueAsString(orderEntity));

            List<ManagerEntity> managers = managerService.getAllManagersForProvider(orderEntity.getProviderId());

            if (managers.isEmpty()) {
                throw new GeneralException("manager list for provider id is null");
            }

            List<UUID> managerIds = null;
            managerIds = managersOrderWebSocketHandler.sendMessageToAll(
                orderEntity.getProviderId(),
                objectMapper.writeValueAsString(orderEntity)
            );

            if (!managerIds.isEmpty()) {
                orderEntity.setStatus(OrderStatus.WAITING_MANAGER);
                orderEntity.setTimeSentToManager(LocalDateTime.now());
                return orderRepository.save(orderEntity);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return orderEntity;
    }

    public Boolean respondToBookingRequest(UserEntity managerUser, ManagersOrderResponseInput input) {
        ManagerEntity manager = managerService.getManager(managerUser);
        Optional<OrderEntity> orderEntityOptional = orderRepository.findById(input.orderId());
        if (orderEntityOptional.isEmpty()) {
            throw new GeneralException("no order found");
        }
        OrderEntity orderEntity = orderEntityOptional.get();


        if (orderEntity.getStatus().equals(OrderStatus.WAITING_MANAGER)) {
            throw new GeneralException("wrong order status");
        }

        if (input.accept()) {
            orderEntity.setStatus(OrderStatus.ACTIVE);
            orderEntity.setManagerId(manager.getId());
            orderEntity.setManagerAcceptedAt(LocalDateTime.now());
        } else {
            orderEntity.setStatus(OrderStatus.CANCELLED_BY_MANAGER);
        }

        orderEntity = orderRepository.save(orderEntity);


        boolean sentToUser = false;
        try {
            sentToUser = userOrderWebSocketHandler.sendMessage(orderEntity.getUser(), objectMapper.writeValueAsString(input));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if (input.accept()) {
            createNewBooking(orderEntity);
        }

        managerService.getAllManagersForProvider(orderEntity.getProviderId());


        return sentToUser;
    }


    public BookingEntity createNewBooking(OrderEntity orderEntity) {
        BookingEntity booking = BookingEntity.builder()
            .categoryId(orderEntity.getCategoryId())
            .status(BookingStatus.PENDING)
            .carPlateNumber(orderEntity.getCarPlateNumber())
            .productId(orderEntity.getProductId())
            .providerId(orderEntity.getProviderId())
            .productDetailsId(orderEntity.getPackageId())
            .schedulingTime(orderEntity.getSchedulingTime())
            .schedulingDate(orderEntity.getSchedulingDate())
            .managerId(orderEntity.getManagerId())
            .userId(orderEntity.getUser().getId())
            .build();

        return bookingRepository.save(booking);
    }

}
