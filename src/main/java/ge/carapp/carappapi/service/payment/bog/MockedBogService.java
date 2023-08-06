package ge.carapp.carappapi.service.payment.bog;

import ge.carapp.carappapi.config.AppConfig;
import ge.carapp.carappapi.core.Language;
import ge.carapp.carappapi.models.bog.AuthenticationResponse;
import ge.carapp.carappapi.models.bog.details.OrderDetails;
import ge.carapp.carappapi.models.bog.order.AutomaticOrderRequest;
import ge.carapp.carappapi.models.bog.order.BogLink;
import ge.carapp.carappapi.models.bog.order.OnHoldAmount;
import ge.carapp.carappapi.models.bog.order.OrderLinks;
import ge.carapp.carappapi.models.bog.order.OrderRequest;
import ge.carapp.carappapi.models.bog.order.OrderResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Profile("dev")
public class MockedBogService implements IBogService {
    public MockedBogService(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    private final AppConfig appConfig;
    private ConcurrentHashMap<UUID, String> paymentIds = new ConcurrentHashMap<>();


    @Override
    public Mono<AuthenticationResponse> authenticate() {
        return Mono.just(
            AuthenticationResponse.builder()
                .accessToken("TEST_ACCESS_TOKEN")
                .expiresIn(123)
                .tokenType("token")
                .build()

        );
    }

    @Override
    public Mono<OrderResponse> createOrder(OrderRequest order, Language language, String token) {
        UUID id = UUID.randomUUID();
        paymentIds.put(id, order.externalOrderId());

        return Mono.just(OrderResponse.builder()
            .id(id)
            .links(new OrderLinks(new BogLink("test link"), new BogLink("%s/mock/payment/%s".formatted(appConfig.getWebUrl(), order.externalOrderId()))))

            .build());
    }

    @Override
    public Mono<OrderResponse> createOrderBySavedCard(OrderRequest order, Language language, UUID orderId, String token) {
        return Mono.just(OrderResponse.builder()
            .id(UUID.randomUUID())
            .links(new OrderLinks(new BogLink("test link"), new BogLink("%s/mock/payment/%s".formatted(appConfig.getWebUrl(), order.externalOrderId()))))
            .build());
    }

    @Override
    public Mono<OrderDetails> retrieveOrderDetails(UUID orderId, String token) {
        return Mono.just(OrderDetails.builder()
            .orderId(orderId)
            .externalOrderId(paymentIds.get(orderId))
            .build());
    }

    @Override
    public Mono<Boolean> saveCardFromOrder(UUID orderId, String token) {
        return Mono.just(true);
    }

    @Override
    public Mono<Boolean> saveCardFromOrderForAutomaticPayments(UUID orderId, String token) {
        return Mono.just(true);
    }

    @Override
    public Mono<OrderResponse> createAutomaticOrderBySavedCard(AutomaticOrderRequest order, UUID parentOrderId, String token) {
        return Mono.just(OrderResponse.builder()
            .id(UUID.randomUUID())
            .links(new OrderLinks(new BogLink("test link"), new BogLink("%s/mock/payment/%s".formatted(appConfig.getWebUrl(), order.externalOrderId()))))
            .build());
    }

    @Override
    public Mono<Boolean> deleteSavedCard(UUID orderId, String token) {
        return Mono.just(true);
    }

    @Override
    public Mono<Boolean> confirmPreAuthorization(UUID orderId, String token, OnHoldAmount onHoldAmount) {
        return Mono.just(true);
    }

    @Override
    public Mono<Boolean> rejectPreAuthorization(UUID orderId, String token) {
        return Mono.just(true);
    }

    @Override
    public Mono<Boolean> refund(UUID orderId, String refundAmount, String token) {
        return Mono.just(true);
    }
}
