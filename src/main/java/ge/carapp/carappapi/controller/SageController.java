package ge.carapp.carappapi.controller;

import ge.carapp.carappapi.service.OpenAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;


@Controller
@RequiredArgsConstructor
public class SageController {

    private final OpenAIService openAIService;

    @GetMapping("/sage")
    @ResponseBody
    public Flux<ServerSentEvent<String>> sage(@RequestParam("q") String question) {
        return openAIService.askGpt(question)
            .map(chunks -> ServerSentEvent.<String> builder()
                .data(chunks)
                .build());
    }

}
