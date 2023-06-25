package ge.carapp.carappapi.schema.graphql

data class InitializePaymentInput(
    val idempotencyKey: String,
    val isAutomatic: Boolean = true,
    val totalAmount: Double,
    val unitPrice: Double,
    val saveCard: Boolean,
)
