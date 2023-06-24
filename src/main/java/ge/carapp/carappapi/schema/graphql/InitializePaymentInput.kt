package ge.carapp.carappapi.schema.graphql

data class InitializePaymentInput(
    val idempotencyKey: String,
    val isAutomatic: Boolean = true
)
