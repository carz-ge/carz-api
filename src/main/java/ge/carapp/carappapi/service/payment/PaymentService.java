package ge.carapp.carappapi.service.payment;

import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.models.bog.AuthenticationResponse;
import ge.carapp.carappapi.models.bog.order.Buyer;
import ge.carapp.carappapi.models.bog.order.OrderRequest;
import ge.carapp.carappapi.models.bog.order.ProductBasket;
import ge.carapp.carappapi.models.bog.order.PurchaseInfo;
import ge.carapp.carappapi.models.bog.order.RedirectUrls;
import ge.carapp.carappapi.schema.Currency;
import ge.carapp.carappapi.schema.graphql.InitializePaymentWithSavedCardsInput;
import ge.carapp.carappapi.schema.payment.PaymentInfoSchema;
import ge.carapp.carappapi.schema.graphql.InitializePaymentInput;
import ge.carapp.carappapi.schema.payment.OrderProcessingResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final BogService bogService;


    public Mono<OrderProcessingResponse> createOrder(@NotNull UserEntity user, @NotNull InitializePaymentInput input) {
        Mono<AuthenticationResponse> authentication = bogService.authenticate();
        String orderId = input.getIdempotencyKey();

        Buyer buyer = Buyer.builder()
            .fullName("სატესტო სახელი და გვარი")
            .build();

        List<ProductBasket> productBaskets = List.of(
            ProductBasket.builder()
                .productId("product123")
                .quantity(1)
                .unitPrice(1d)
                .description("სატესტო აღწერა")
                .build()
        );

        PurchaseInfo purchaseInfo = PurchaseInfo.builder()
            .totalAmount(1d)
            .basket(productBaskets)
            .currency(Currency.GEL.name())
            .build();

        RedirectUrls redirectUrls = RedirectUrls.builder()
            .success("https://api2.carz.ge/payment/redirect/%s/reject".formatted(orderId))
            .fail("https://api2.carz.ge/payment/redirect/%s/success".formatted(orderId))
            .build();

        var order = OrderRequest.builder()
            .callbackUrl("https://api2.carz.ge/payment/%s".formatted(orderId))
            .externalOrderId(orderId)
            .capture(input.isAutomatic() ? "automatic" : "manual")
            .buyer(buyer)
            .purchaseUnits(purchaseInfo)
            .redirectUrls(redirectUrls)
            .ttl(600) // 10 min
            .build();

        return authentication.mapNotNull(AuthenticationResponse::accessToken)
            .flatMap(token -> bogService.createOrder(order, user.getLanguage(), token))
            .log()
            .doOnError(e -> log.error("error occurred after creating order", e))
            .map(res -> {
                String bogOrderId = res.id();
                String redirectLink = res.links().redirect().href();
                return OrderProcessingResponse.builder()
                    .idempotencyKey(input.getIdempotencyKey())
                    .orderId(bogOrderId)
                    .redirectLink(redirectLink)
                    .build();
            });
    }


    public Mono<OrderProcessingResponse> createOrderBySavedCard(@NotNull UserEntity user, @NotNull InitializePaymentWithSavedCardsInput input) {
        Mono<AuthenticationResponse> authentication = bogService.authenticate();
        String orderId = input.getIdempotencyKey();

        Buyer buyer = Buyer.builder()
            .fullName("სატესტო სახელი და გვარი")
            .build();

        List<ProductBasket> productBaskets = List.of(
            ProductBasket.builder()
                .productId("product123")
                .quantity(1)
                .unitPrice(1d)
                .description("სატესტო აღწერა")
                .build()
        );

        PurchaseInfo purchaseInfo = PurchaseInfo.builder()
            .totalAmount(1d)
            .basket(productBaskets)
            .currency(Currency.GEL.name())
            .build();

        RedirectUrls redirectUrls = RedirectUrls.builder()
            .success("https://api2.carz.ge/payment/redirect/%s/reject".formatted(orderId))
            .fail("https://api2.carz.ge/payment/redirect/%s/success".formatted(orderId))
            .build();

        var order = OrderRequest.builder()
            .callbackUrl("https://api2.carz.ge/payment/%s".formatted(orderId))
            .externalOrderId(orderId)
            .capture(input.isAutomatic() ? "automatic" : "manual")
            .buyer(buyer)
            .purchaseUnits(purchaseInfo)
            .redirectUrls(redirectUrls)
            .ttl(600) // 10 min
            .build();

        return authentication.mapNotNull(AuthenticationResponse::accessToken)
            .flatMap(token -> bogService.createOrderBySavedCard(order, user.getLanguage(), input.getOrderId(), token))
            .log()
            .doOnError(e -> log.error("error occurred after creating order", e))
            .map(res -> {
                String bogOrderId = res.id();
                String redirectLink = res.links().redirect().href();
                return OrderProcessingResponse.builder()
                    .idempotencyKey(input.getIdempotencyKey())
                    .orderId(bogOrderId)
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
    public Mono<Boolean> confirmPreAuthorization(@Nullable UserEntity user, @NotNull UUID orderId) {
        Mono<AuthenticationResponse> authentication = bogService.authenticate();
        return authentication.mapNotNull(AuthenticationResponse::accessToken)
            .flatMap(token -> bogService.confirmPreAuthorization(orderId, token))
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
