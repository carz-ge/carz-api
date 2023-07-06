package ge.carapp.carappapi.service.payment;

import ge.carapp.carappapi.config.ProfileConfig;
import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.models.bog.AuthenticationResponse;
import ge.carapp.carappapi.models.bog.order.Buyer;
import ge.carapp.carappapi.models.bog.order.OnHoldAmount;
import ge.carapp.carappapi.models.bog.order.OrderRequest;
import ge.carapp.carappapi.models.bog.order.OrderResponse;
import ge.carapp.carappapi.models.bog.order.ProductBasket;
import ge.carapp.carappapi.models.bog.order.PurchaseInfo;
import ge.carapp.carappapi.models.bog.order.RedirectUrls;
import ge.carapp.carappapi.schema.Currency;
import ge.carapp.carappapi.schema.graphql.InitializePaymentInput;
import ge.carapp.carappapi.schema.graphql.InitializePaymentWithSavedCardsInput;
import ge.carapp.carappapi.schema.graphql.PaymentConfirmDetailsInput;
import ge.carapp.carappapi.schema.payment.OrderProcessingResponse;
import ge.carapp.carappapi.schema.payment.PaymentInfoSchema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final ProfileConfig profileConfig;
    private final BogService bogService;

    public Mono<OrderProcessingResponse> createOrder(@NotNull UserEntity user,
                                                     @NotNull OrderRequest order,
                                                     boolean saveCard) {
        Mono<AuthenticationResponse> authentication = bogService.authenticate();
        Mono<String> authToken = authentication.mapNotNull(AuthenticationResponse::accessToken);

        Mono<OrderResponse> createOrderResult = authToken
            .flatMap(token -> bogService.createOrder(order, user.getLanguage(), token))
            .log()
            .doOnError(e -> log.error("error occurred after creating order", e));

        if (saveCard) {
            createOrderResult
                .flatMap(res -> authToken
                    .flatMap(token -> bogService
                        .saveCardFromOrder(res.id(), token)))
                .log();
        }

        return createOrderResult
            .map(res -> {
                UUID bogOrderId = res.id();
                String redirectLink = res.links().redirect().href();
                return OrderProcessingResponse.builder()
                    .bogOrderId(bogOrderId)
                    .redirectLink(redirectLink)
                    .build();
            });
    }

    public Mono<OrderProcessingResponse> createOrder(@NotNull UserEntity user, @NotNull InitializePaymentInput input) {


        OrderRequest order = createOrderRequest(input.getOrderId(),
            input.getUnitPrice(),
            input.getTotalAmount(),
            input.isAutomatic(),
            null,
            "",
            ""
        );
        return createOrder(user, order, input.getSaveCard());

    }

    public static OrderRequest createOrderRequest(
        UUID orderId,
        double unitPrice,
        double totalAmount,
        boolean isAutomatic,
        UUID productId,
        String productName,
        String buyerFullName
    ) {


        List<ProductBasket> productBaskets = List.of(
            ProductBasket.builder()
                .productId(productId.toString())
                .quantity(1)
                .unitPrice(unitPrice)
                .description(productName)
                .build()
        );

        PurchaseInfo purchaseInfo = PurchaseInfo.builder()
            .totalAmount(totalAmount)
            .basket(productBaskets)
            .currency(Currency.GEL.name())
            .build();

        RedirectUrls redirectUrls = RedirectUrls.builder()
            .success("https://api2.carz.ge/payment/redirect/%s/success".formatted(orderId))
            .fail("https://api2.carz.ge/payment/redirect/%s/reject".formatted(orderId))
            .build();

        Buyer buyer = Buyer.builder()
            .fullName(buyerFullName)
            .build();

        return OrderRequest.builder()
            .callbackUrl("https://api2.carz.ge/payment/%s".formatted(orderId))
            .externalOrderId(orderId.toString())
            .capture(isAutomatic ? "automatic" : "manual")
            .buyer(buyer)
            .purchaseUnits(purchaseInfo)
            .redirectUrls(redirectUrls)
            .ttl(600) // 10 min
            .build();
    }


    public Mono<OrderProcessingResponse> createOrderBySavedCard(@NotNull UserEntity user, @NotNull InitializePaymentWithSavedCardsInput input) {
        Mono<AuthenticationResponse> authentication = bogService.authenticate();

        OrderRequest order = createOrderRequest(input.getOrderId(),
            input.getUnitPrice(),
            input.getTotalAmount(),
            input.isAutomatic(),
            null,
            "",
            ""
        );

        return authentication.mapNotNull(AuthenticationResponse::accessToken)
            .flatMap(token -> bogService.createOrderBySavedCard(order, user.getLanguage(), input.getBogOrderId(), token))
            .log()
            .doOnError(e -> log.error("error occurred after creating order", e))
            .map(res -> {
                UUID bogOrderId = res.id();
                String redirectLink = res.links().redirect().href();
                return OrderProcessingResponse.builder()
                    .bogOrderId(bogOrderId)
                    .redirectLink(redirectLink)
                    .build();
            });
    }


    public Mono<PaymentInfoSchema> retrievePaymentInfo(@NotNull UserEntity user, @NotNull UUID orderId) {
        Mono<AuthenticationResponse> authentication = bogService.authenticate();
        return authentication.mapNotNull(AuthenticationResponse::accessToken)
            .flatMap(token -> bogService.retrieveOrderDetails(orderId, token))
            .log()
            .doOnError(e -> log.error("error occurred after retrieve Payment Info order", e))
            .map(PaymentInfoSchema::from);
    }

    public Mono<Boolean> saveCard(@Nullable UserEntity user, @NotNull UUID orderId) {
        Mono<AuthenticationResponse> authentication = bogService.authenticate();
        return authentication.mapNotNull(AuthenticationResponse::accessToken)
            .flatMap(token -> bogService.saveCardFromOrder(orderId, token))
            .log()
            .doOnError(e -> log.error("error occurred after save Card", e));

    }

    @NotNull
    public Mono<Boolean> removeCard(@Nullable UserEntity user, @NotNull UUID orderId) {
        Mono<AuthenticationResponse> authentication = bogService.authenticate();
        return authentication.mapNotNull(AuthenticationResponse::accessToken)
            .flatMap(token -> bogService.deleteSavedCard(orderId, token))
            .log()
            .doOnError(e -> log.error("error occurred after remove Card", e));

    }

    @NotNull
    public Mono<Boolean> confirmPreAuthorization(@Nullable UserEntity user, @NotNull UUID orderId,
                                                 PaymentConfirmDetailsInput detailsInput) {
        Mono<AuthenticationResponse> authentication = bogService.authenticate();

        return authentication.mapNotNull(AuthenticationResponse::accessToken)
            .flatMap(token -> {
                OnHoldAmount onHoldAmount = null;
                if (Objects.nonNull(detailsInput)) {
                    onHoldAmount = new OnHoldAmount(detailsInput.getAmount(), detailsInput.getDescription());
                }
                return bogService.confirmPreAuthorization(orderId, token, onHoldAmount);
            })
            .log()
            .doOnError(e -> log.error("error occurred after confirm PreAuthorization", e));

    }

    @NotNull
    public Mono<Boolean> rejectPreAuthorization(@Nullable UserEntity user, @NotNull UUID orderId) {
        Mono<AuthenticationResponse> authentication = bogService.authenticate();
        return authentication.mapNotNull(AuthenticationResponse::accessToken)
            .flatMap(token -> bogService.rejectPreAuthorization(orderId, token))
            .log()
            .doOnError(e -> log.error("error occurred after reject PreAuthorizationr", e));

    }

    @NotNull
    public Mono<Boolean> refund(@Nullable UserEntity user, @NotNull UUID orderId, String refundAmount) {
        Mono<AuthenticationResponse> authentication = bogService.authenticate();
        return authentication.mapNotNull(AuthenticationResponse::accessToken)
            .flatMap(token -> bogService.refund(orderId, refundAmount, token))
            .log()
            .doOnError(e -> log.error("error occurred after refund", e));

    }
}
