package ge.carapp.carappapi.schema;

import ge.carapp.carappapi.entity.CardEntity;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CardSchema(
    UUID id,
    String pan,
    String cardType,
    String expirationDate
) {


    public static CardSchema convert(CardEntity cardEntity) {
        return  CardSchema.builder()
            .id(cardEntity.getId())
            .cardType(cardEntity.getCardType())
            .pan(cardEntity.getPan())
            .expirationDate(cardEntity.getExpirationDate())
            .build();
    }
}
