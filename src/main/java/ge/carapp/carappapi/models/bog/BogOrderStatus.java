package ge.carapp.carappapi.models.bog;

public enum BogOrderStatus {
    CREATED, // payment request is created
    PROCESSING, // payment is being processed
    COMPLETED, // payment process has been completed
    REJECTED,// payment process has been unsuccessfully completed
    REFUND_REQUESTED, // refunding of the amount is requested
    REFUNDED, // payment amount has been returned
    REFUNDED_PARTIALLY; // payment amount has been partially refunded.

    public String toLower() {
        return this.name().toLowerCase();
    }
}
