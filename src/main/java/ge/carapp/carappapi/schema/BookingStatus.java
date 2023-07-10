package ge.carapp.carappapi.schema;

public enum BookingStatus {
    PENDING,
    STARTED,
    FINISHED,

    // By Customer
    CANCELED,

    // BY manager
    DECLINED,
}
