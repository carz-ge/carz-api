package ge.carapp.carappapi.service.payment.bog;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import ge.carapp.carappapi.config.BogConfig;
import ge.carapp.carappapi.core.Language;
import ge.carapp.carappapi.exception.GeneralException;
import ge.carapp.carappapi.models.bog.AuthenticationResponse;
import ge.carapp.carappapi.models.bog.details.OrderDetails;
import ge.carapp.carappapi.models.bog.order.AutomaticOrderRequest;
import ge.carapp.carappapi.models.bog.order.OnHoldAmount;
import ge.carapp.carappapi.models.bog.order.OrderRequest;
import ge.carapp.carappapi.models.bog.order.OrderResponse;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

// Docs: https://api.bog.ge/docs/en/payments/introduction

@Service
@Slf4j
@Profile("prod")
public class BogService implements IBogService{
    private final BogConfig config;

    private final WebClient client;
    private final ObjectMapper objectMapper = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
        .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    public BogService(final BogConfig config) {
        this.config = config;
        this.client = config.bogApiClient();
    }


    public Mono<AuthenticationResponse> authenticate() {
        WebClient.ResponseSpec retrieve = client
            .post()
            .uri(config.getAuthUrl())
            .header(HttpHeaders.AUTHORIZATION, "Basic " + config.getSecret())
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .bodyValue("grant_type=client_credentials")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve();
        return handleResponse(retrieve, AuthenticationResponse.class);
    }


    public Mono<OrderResponse> createOrder(@NotNull OrderRequest order, Language language, @NotNull String token) {
        Language lang = Objects.requireNonNullElse(language, Language.KA);
        try {
            String request = objectMapper.writeValueAsString(order);
            log.info("Sending order request to bog: {}", request);

            return handleResponse(client
                    .post()
                    .uri(config.getApiUrl() + "/ecommerce/orders")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .header(HttpHeaders.ACCEPT_LANGUAGE, lang.name().toLowerCase())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                , OrderResponse.class);
        } catch (JsonProcessingException e) {
            log.error("Could not convert order to json", e);
            throw new GeneralException("could not process json for orders");
        }
    }

    public Mono<OrderResponse> createOrderBySavedCard(@NotNull OrderRequest order, Language language,
                                                      @NotNull UUID orderId, @NotNull String token) {
        Language lang = Objects.requireNonNullElse(language, Language.KA);
        try {
            String request = objectMapper.writeValueAsString(order);
            log.info("Sending order request to bog: {}", request);

            return handleResponse(client
                    .post()
                    .uri(config.getApiUrl() + "/ecommerce/orders/" + orderId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.ACCEPT_LANGUAGE, lang.name().toLowerCase())
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                , OrderResponse.class);
        } catch (JsonProcessingException e) {
            log.error("Could not convert order to json", e);
            throw new GeneralException("could not process json for orders");
        }
    }

    public Mono<OrderDetails> retrieveOrderDetails(@NotNull UUID orderId, @NotNull String token) {
        return handleResponse(client
                .get()
                .uri(config.getApiUrl() + "/receipt/" + orderId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
            , OrderDetails.class);
    }

    public Mono<Boolean> saveCardFromOrder(@NotNull UUID orderId, @NotNull String token) {
        return handleAcceptStatusResponse(client
            .put()
            .uri(config.getApiUrl() + "/orders/" + orderId + "/cards")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token));
    }

    // TODo https://api.bog.ge/docs/en/payments/saved-card/recurrent-payment
    public Mono<Boolean> saveCardFromOrderForAutomaticPayments(@NotNull UUID orderId, @NotNull String token) {
        return handleAcceptStatusResponse(
            client.put()
            .uri(config.getApiUrl() + "/orders/" + orderId + "/subscriptions")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token));
    }

    public Mono<OrderResponse> createAutomaticOrderBySavedCard(@NotNull AutomaticOrderRequest order,
                                                                     @NotNull UUID parentOrderId, @NotNull String token) {
        try {
            String request = objectMapper.writeValueAsString(order);
            log.info("Sending order request to bog: {}", request);

            return handleResponse(client
                    .post()
                    .uri(config.getApiUrl() + "/ecommerce/orders/" + parentOrderId +"/subscribe")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                , OrderResponse.class);
        } catch (JsonProcessingException e) {
            log.error("Could not convert order to json", e);
            throw new GeneralException("could not process json for orders");
        }
    }

    public Mono<Boolean> deleteSavedCard(@NotNull UUID orderId, @NotNull String token) {
        return client
            .delete()
            .uri(config.getApiUrl() + "/charges/card/" + orderId)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .exchangeToMono(
                r -> {
                    if (HttpStatus.ACCEPTED.equals(r.statusCode())) {
                        return Mono.just(true);
                    }

                    return r.bodyToMono(String.class)
                        .map(e -> false);
                }
            )
            .doOnError(e -> {
                log.info("handleResponse On Error: {}", e.getMessage());
                if (e instanceof WebClientResponseException err) {
                    String errorBody = err.getResponseBodyAsString();
                    log.error("Request error message: %s".formatted(errorBody), err);
                }
            });
    }


    public Mono<Boolean> confirmPreAuthorization(@NotNull UUID orderId, @NotNull String token,
                                                 OnHoldAmount onHoldAmount) {
        WebClient.RequestBodySpec requestBodySpec = client
            .post()
            .uri(config.getApiUrl() + "/payment/authorization/approve/" + orderId)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        if (Objects.nonNull(onHoldAmount)) {
            return handleAcceptStatusResponse(requestBodySpec
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(onHoldAmount));
        }

        return handleAcceptStatusResponse(requestBodySpec);
    }

    public Mono<Boolean> rejectPreAuthorization(@NotNull UUID orderId, @NotNull String token) {
        return handleAcceptStatusResponse(client
            .post()
            .uri(config.getApiUrl() + "/payment/authorization/cancel/" + orderId)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON));
    }


    public Mono<Boolean> refund(@NotNull UUID orderId, String refundAmount, @NotNull String token) {
        WebClient.RequestHeadersSpec<?> req = client
            .post()
            .uri(config.getApiUrl() + "/payment/refund/" + orderId)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(Map.of("amount", refundAmount));
        return handleAcceptStatusResponse(req);
    }


    @org.jetbrains.annotations.NotNull
    private static <T> Mono<T> handleResponse(WebClient.ResponseSpec retrieve, Class<T> responseClass) {
        return retrieve
            .toEntity(responseClass)
            .doOnError(e -> {
                log.info("handleResponse On Error: {}", e.getMessage());
                if (e instanceof WebClientResponseException err) {
                    String errorBody = err.getResponseBodyAsString();
                    log.error("Request error message: %s".formatted(errorBody), err);
                }
            })
            .mapNotNull(HttpEntity::getBody);
    }

    @org.jetbrains.annotations.NotNull
    private static Mono<Boolean> handleAcceptStatusResponse(WebClient.RequestBodySpec req) {
        return req
            .exchangeToMono(
                r -> {
                    if (HttpStatus.ACCEPTED.equals(r.statusCode())) {
                        return Mono.just(true);
                    }

                    return r.bodyToMono(String.class)
                        .map(e -> false);
                }
            )
            .doOnError(e -> {
                log.info("handleResponse On Error: {}", e.getMessage());
                if (e instanceof WebClientResponseException err) {
                    String errorBody = err.getResponseBodyAsString();
                    log.error("Request error message: %s".formatted(errorBody), err);
                }
            });
    }

    //TODO

    @org.jetbrains.annotations.NotNull
    private static Mono<Boolean> handleAcceptStatusResponse(WebClient.RequestHeadersSpec<?> req) {
        return req
            .exchangeToMono(
                r -> {
                    if (HttpStatus.ACCEPTED.equals(r.statusCode())) {
                        return Mono.just(true);
                    }

                    return r.bodyToMono(String.class)
                        .map(e -> false);
                }
            )
            .doOnError(e -> {
                log.info("handleResponse On Error: {}", e.getMessage());
                if (e instanceof WebClientResponseException err) {
                    String errorBody = err.getResponseBodyAsString();
                    log.error("Request error message: %s".formatted(errorBody), err);
                }
            });
    }


}

