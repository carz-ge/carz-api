package ge.carapp.carappapi.controller;

import ge.carapp.carappapi.service.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;


@Controller
@RequiredArgsConstructor
public class OpenAIController {

    private final OpenAIService openAIService;

    @QueryMapping
    public Flux<String> askSage(@Argument String question) {
        return openAIService.askGpt(question);
    }

}
