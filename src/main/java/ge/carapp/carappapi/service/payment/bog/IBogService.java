package ge.carapp.carappapi.service.payment.bog;

import ge.carapp.carappapi.core.Language;
import ge.carapp.carappapi.models.bog.AuthenticationResponse;
import ge.carapp.carappapi.models.bog.details.OrderDetails;
import ge.carapp.carappapi.models.bog.order.AutomaticOrderRequest;
import ge.carapp.carappapi.models.bog.order.OnHoldAmount;
import ge.carapp.carappapi.models.bog.order.OrderRequest;
import ge.carapp.carappapi.models.bog.order.OrderResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IBogService {
    Mono<AuthenticationResponse> authenticate();
    
    Mono<OrderResponse> createOrder(OrderRequest order, Language language, String token);

    
    Mono<OrderResponse> createOrderBySavedCard(OrderRequest order, Language language, UUID orderId, String token);
    
    Mono<OrderDetails> retrieveOrderDetails(UUID orderId, String token) ;
    
    Mono<Boolean> saveCardFromOrder(UUID orderId, String token) ;
    
    Mono<Boolean> saveCardFromOrderForAutomaticPayments(UUID orderId, String token);
    
    Mono<OrderResponse> createAutomaticOrderBySavedCard(AutomaticOrderRequest order, UUID parentOrderId, String token);
    
    Mono<Boolean> deleteSavedCard(UUID orderId, String token);
    
    Mono<Boolean> confirmPreAuthorization(UUID orderId, String token, OnHoldAmount onHoldAmount);
    
    Mono<Boolean> rejectPreAuthorization(UUID orderId, String token);
    
    Mono<Boolean> refund(UUID orderId, String refundAmount, String token);
}

