package ge.carapp.carappapi.schema.graphql

import java.util.UUID

data class InitializePaymentInput(
    val orderId: UUID,
    val isAutomatic: Boolean = true,
    val totalAmount: Double,
    val unitPrice: Double,
    val saveCard: Boolean,
)
