package ge.carapp.carappapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Data
@Configuration
@ConfigurationProperties(prefix = "bog")
public class BogConfig {

    private String authUrl;
    private String apiUrl;
    private String secret;

    public WebClient bogApiClient() {
        return WebClient
            .builder()
            .build();
    }
}
