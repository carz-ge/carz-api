package ge.carapp.carappapi.schema.graphql

import java.util.UUID

data class InitializePaymentWithSavedCardsInput(
    val orderId: UUID,
    val isAutomatic: Boolean = true,
    val bogOrderId: UUID,
    val totalAmount: Double,
    val unitPrice: Double,
)
