//package ge.carapp.carappapi.service.notification.messenger;
//
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//
//import java.util.HashMap;
//import java.util.Map;
//
//
//@Service
//@Slf4j
//public class MessengerService {
//
//    private final WebClient client = WebClient
//        .builder()
//        .baseUrl("https://graph.facebook.com")
//        .build();
//
//    public void sendMessage(String recipientId, String accessToken, String message) {
//
//        Map<String, Map<String, String>> response = new HashMap<>();
//
//        response.put("recipient", Map.of("id", recipientId));
//        response.put("message", Map.of("text", message));
//
//
//        Mono<String> resp = client
//            .post()
//            .uri("/v17.0/me/messages?access_token=" + accessToken)
//            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//            .accept(MediaType.APPLICATION_JSON)
//            .bodyValue(response)
//            .retrieve()
//            .bodyToMono(String.class)
//            .log();
//
//        resp.block();
//
//    }
//
//
//    public static void main(String[] args) {
//        MessengerService messageSender = new MessengerService();
//        messageSender.sendMessage("","", "test message");
//
//    }
//
//}
