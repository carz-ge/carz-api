package ge.carapp.carappapi.entity;

public enum OtpStatus {
    CREATED,
    SENT,
    SEND_FAILED,
    VERIFIED,
    EXPIRED,
    MISSED,
    EXCEEDED_ATTEMPTS
}
