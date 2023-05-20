package ge.carapp.carappapi.entity.json_converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
@Converter
public abstract class ListConverter<T> implements AttributeConverter<List<T>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

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
            JavaType type = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, getTypeParameterClass());
            return objectMapper.readValue(dbData, type);
        } catch (JsonProcessingException e) {
            log.error("JSON reading error", e);
            return Collections.emptyList();
        }
    }


    protected abstract Class<T> getTypeParameterClass();

    //    private Class<T> getTypeParameterClass() {
    //        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
    //        return (Class<T>) parameterizedType.getActualTypeArguments()[0];
    //    }
}
