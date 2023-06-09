package ge.carapp.carappapi.schema.graphql;

public record PushNotificationInput(
    String deviceToken,
    String title,
    String text
) {
}
