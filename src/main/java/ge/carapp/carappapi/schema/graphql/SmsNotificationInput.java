package ge.carapp.carappapi.schema.graphql;

public record SmsNotificationInput(
    String phone,
    String text
) {
}
