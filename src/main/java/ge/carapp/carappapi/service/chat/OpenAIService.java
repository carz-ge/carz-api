package ge.carapp.carappapi.service.chat;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import ge.carapp.carappapi.exception.GeneralException;
import ge.carapp.carappapi.models.openai.ChatMessage;
import ge.carapp.carappapi.models.openai.CompletionRequest;
import ge.carapp.carappapi.models.openai.EventData;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class OpenAIService {

    private static final Duration TIMEOUT = Duration.ofSeconds(5L);
    private static final int MAX_TOKENS = 50;
    private static final Double TEMPERATURE = 0.8;

    // https://platform.openai.com/docs/api-reference/chat
    private static final String MODEL = "gpt-3.5-turbo";


    @Value("${openai.url}")
    private String openAiUrl;


    @Value("${openai.key}")
    private String openAiKey;


    private WebClient client;

    private final ObjectMapper objectMapper = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
        .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @PostConstruct
    public void init() {
        client = WebClient.builder()
            .baseUrl(openAiUrl)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + openAiKey)
            .build();
    }

    public Flux<String> askGpt(String prompt) {
        List<ChatMessage> messageList = List.of(
            ChatMessage.builder()
                .content(prompt)
                .role("user")
                .build()
        );
        return gptChat(messageList);
    }

    public Flux<String> gptChat(List<ChatMessage> messageList) {
        CompletionRequest request = CompletionRequest.builder()
            .model(MODEL)
            .messages(messageList)
            .stream(true)
            .temperature(TEMPERATURE)
            .maxTokens(MAX_TOKENS)
            .build();

        try {
            String requestValue = objectMapper.writeValueAsString(request);
            log.info("gpt request: {}", requestValue);

            return client
                .post()
                .uri("/v1/chat/completions")
                .bodyValue(requestValue)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(String.class)
                .mapNotNull(event -> {
                    try {
                        log.info("event {}", event);
                        if ("[DONE]".equals(event)) {
                            return null;
                        }
                        String jsonData = event.substring(event.indexOf("{"), event.lastIndexOf("}") + 1);
                        return objectMapper.readValue(jsonData, EventData.class);
                    } catch (JsonProcessingException | StringIndexOutOfBoundsException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .takeUntil(event -> Objects.isNull(event) || (
                    Objects.isNull(event.choices().get(0).finishReason())
                        && Objects.isNull(event.choices().get(0).delta().content())
                        && Objects.isNull(event.choices().get(0).delta().role())
                ))
                .skipUntil(event -> {
                    String content = event.choices().get(0).delta().content();
                    return !(Objects.isNull(content) || content.equals("\n"));
                })
                .map(event -> Objects.requireNonNullElse(event.choices().get(0).delta().content(), ""));
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneralException(e.getMessage());
        }
    }
}
