package ge.carapp.carappapi.entity.json_converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Converter
public class ListConverter<T> implements AttributeConverter<List<T>, String> {
    ObjectMapper objectMapper = new ObjectMapper(); // TODO DI

    @Override
    public String convertToDatabaseColumn(List<T> attribute) {
        String customerInfoJson = null;
        try {
            customerInfoJson = objectMapper.writeValueAsString(attribute);
        } catch (final JsonProcessingException e) {
            log.error("JSON writing error", e);
        }

        return customerInfoJson;
    }

    @Override
    public List<T> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<List<T>>() {
            });
        } catch (JsonProcessingException e) {
            log.error("JSON reading error", e);
            return Collections.emptyList();
        }
    }
}
