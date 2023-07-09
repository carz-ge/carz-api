package ge.carapp.carappapi.service;

import ge.carapp.carappapi.entity.CardEntity;
import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.models.bog.details.PaymentDetail;
import ge.carapp.carappapi.repository.CardRepository;
import ge.carapp.carappapi.schema.CardSchema;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;


    public CardSchema saveCard(UserEntity user, UUID orderId, UUID bogOrderId, UUID paymentId, PaymentDetail paymentDetail) {
        CardEntity cardEntity = CardEntity.builder()
            .orderId(orderId)
            .bogOrderId(bogOrderId)
            .paymentId(paymentId)
            .user(user)
            .expirationDate(paymentDetail.cardExpiryDate())
            .pan(paymentDetail.payerIdentifier())
            .cardType(paymentDetail.cardType())
            .removed(false)
            .build();

        cardEntity = cardRepository.save(cardEntity);
        return CardSchema.convert(cardEntity);
    }


    public List<CardSchema> listUserCards(UserEntity user) {
        return cardRepository.findAllByUserIdAndRemoved(user.getId(), false)
            .stream().map(CardSchema::convert).toList();
    }

    // TODO add remove card

}
