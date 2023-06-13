package ge.carapp.carappapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "sms")
public class SmsConfig {
    private String apiUrl;

    private String title;

    private String apiKey;
}
