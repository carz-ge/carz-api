package ge.carapp.carappapi.service;

import ge.carapp.carappapi.entity.CardEntity;
import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.exception.GeneralException;
import ge.carapp.carappapi.models.bog.details.PaymentDetail;
import ge.carapp.carappapi.repository.CardRepository;
import ge.carapp.carappapi.schema.CardSchema;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;


    public final CardEntity getByCardIdAndUserId(UUID cardId, UUID userId) {
        return cardRepository.findByIdAndUserId(cardId, userId).orElseThrow(() -> new GeneralException("card not " +
            "found for user"));
    }

    public CardSchema saveCard(UserEntity user, UUID orderId,
                               UUID bogOrderId, UUID paymentId,
                               PaymentDetail paymentDetail, String totalAmount) {

        List<CardEntity> userCards = cardRepository.findAllByUserId(user.getId());

        // check if same card already exists
        Optional<CardEntity> savedCard = userCards.stream().filter(card ->
            card.getCardType().equals(paymentDetail.cardType())
                && card.getPan().equals(paymentDetail.payerIdentifier())
                && card.getExpirationDate().equals(paymentDetail.cardExpiryDate())
        ).findAny();

        CardEntity cardEntity;
        if (savedCard.isEmpty()) {
            cardEntity = CardEntity.builder()
                .orderId(orderId)
                .bogOrderId(bogOrderId)
                .paymentId(paymentId)
                .user(user)
                .expirationDate(paymentDetail.cardExpiryDate())
                .pan(paymentDetail.payerIdentifier())
                .cardType(paymentDetail.cardType())
                .removed(false)
                .totalAmountInGel(totalAmount)
                .build();
        } else {
            cardEntity = savedCard.get();
            if (Boolean.FALSE.equals(cardEntity.getRemoved())) {
                return CardSchema.convert(cardEntity);
            }
            cardEntity.setOrderId(orderId);
            cardEntity.setBogOrderId(bogOrderId);
            cardEntity.setTotalAmountInGel(totalAmount);
            cardEntity.setRemoved(false);
        }
        cardEntity = cardRepository.save(cardEntity);
        return CardSchema.convert(cardEntity);
    }


    public List<CardSchema> listUserCards(UserEntity user) {
        return cardRepository.findAllByUserIdAndRemoved(user.getId(), false)
            .stream().map(CardSchema::convert).toList();
    }

    // TODO add remove card

}
