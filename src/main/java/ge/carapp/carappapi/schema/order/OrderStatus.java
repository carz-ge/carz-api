package ge.carapp.carappapi.schema.order;

public enum OrderStatus {
    NEW,
    PROCESSING,
    PAYED,
    WAITING_MANAGER,
    ACTIVE,
    DELIVERED,
    CANCELLED, // by user
    CANCELLED_BY_MANAGER,
    REJECTED, // by bank
    REIMBURSED,
    FAILED // by server
}
