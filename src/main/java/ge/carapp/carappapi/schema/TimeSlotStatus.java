package ge.carapp.carappapi.schema;

public enum TimeSlotStatus {
    PENDING,
    STARTED,
    FINISHED,

    // By Customer
    CANCELED,

    // BY manager
    DECLINED,
}
