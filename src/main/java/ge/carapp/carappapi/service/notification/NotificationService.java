package ge.carapp.carappapi.service.notification;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class NotificationService {

    private static final String SENDER = "CarApp";

    private final WebClient client;

    private final ObjectMapper objectMapper;
// https://smsoffice.ge/api/v2/send?
    public NotificationService() {
        client = WebClient.create("https://smsoffice.ge/api/v2/send");
        objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
//6f5fc7ba49344723b22e3e52e1aea302
    public boolean sendOtpNotification(String phone, String otp) {
        log.info("sending otp notification to {}, otp {}", phone, otp);
        // TODO
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("destination", phone);
//        map.put("content", "your code is " + otp);
//        map.put("no_sms", false);
//        map.put("key", "63ef00511c28af0caf599c7eb71cda55f50afd62a1724fb51c82282ff12e3ece");
//        map.put("brand_name", SENDER);
        String query = "key=%s&destination=%s&sender=%s&content=%s"
            .formatted(
                "6f5fc7ba49344723b22e3e52e1aea302",
                phone,
                "Vano Ganjelashvili",
                "your code is " + otp
            );
        try {
//            String json = objectMapper.writeValueAsString(map);
            log.info("json {}", query);

            String response = this.client
                .get()
                .uri("/?" + query)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .retrieve()
                .bodyToFlux(String.class)
                .blockLast();
            log.info("sms response {}", response);
            return true;
        } catch (Exception e) {
            log.error("error sending otp notification", e);
            return false;
        }
    }
}
