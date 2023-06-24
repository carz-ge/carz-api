package ge.carapp.carappapi.schema.graphql

import java.util.UUID

data class InitializePaymentWithSavedCardsInput(
    val idempotencyKey: String,
    val isAutomatic: Boolean = true,
    val orderId: UUID
)
