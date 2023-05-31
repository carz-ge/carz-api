package ge.carapp.carappapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class CarAppApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarAppApiApplication.class, args);
    }

//    @Bean
//    ApplicationRunner test() {
//        return a -> {
//            WebClient client = WebClient.create("http://localhost:8080");
//            ParameterizedTypeReference<ServerSentEvent<String>> type
//                = new ParameterizedTypeReference<ServerSentEvent<String>>() {
//            };
//
//            Flux<ServerSentEvent<String>> eventStream = client.get()
//                .uri("/stream-sse")
//                .retrieve()
//                .bodyToFlux(type);
//
//            eventStream.subscribe(
//                content -> log.info("Time: {} - event: name[{}], id [{}], content[{}] ",
//                    LocalTime.now(), content.event(), content.id(), content.data()),
//                error -> log.error("Error receiving SSE:", error),
//                () -> log.info("Completed!!!"));
//        };
//    }
}
