package ge.carapp.carappapi.models.bog;

import lombok.Getter;

@Getter
public enum CardType {
    AMEX("amex"),
    MASTERCARD("mc"),
    VISA("visa");

    final String value;

    CardType(String value) {
        this.value = value;
    }
}
