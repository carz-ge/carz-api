package ge.carapp.carappapi.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ge.carapp.carappapi.entity.OrderEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ofPattern;

public class TimeUtils {

    public static final DateTimeFormatter FORMATTER = ofPattern("yyyy:dd:MM");

    public static void main(String[] args) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);
        JavaTimeModule javaTimeModule = new JavaTimeModule();

//        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(FORMATTER));
//        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(FORMATTER));
        objectMapper.registerModule(javaTimeModule);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        OrderEntity build = OrderEntity.builder()
            .schedulingDate(LocalDate.now())
            .schedulingTime(LocalTime.now())
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        System.out.println(LocalDate.now());
        System.out.println(LocalTime.now());
        System.out.println(LocalDateTime.now());
        System.out.println(objectMapper.writeValueAsString(build));

        OrderEntity order = objectMapper.readValue("{\"scheduling_date\":\"2023-07-11\"," +
                "\"scheduling_time\":\"09:27\"," +
                "\"created_at\":\"2023-07-11T12:27:55.3812469\",\"updated_at\":\"2023-07-11T12:27:55.3812469\"}",
            OrderEntity.class);
        System.out.println(order);


//        String json = new Gson().toJson(build);
//        System.out.println(json);
    }
}
