package ge.carapp.carappapi.controller.admin

import ge.carapp.carappapi.schema.graphql.InitializePaymentInput
import ge.carapp.carappapi.schema.graphql.InitializePaymentWithSavedCardsInput
import ge.carapp.carappapi.schema.payment.OrderProcessingResponse
import ge.carapp.carappapi.schema.payment.PaymentInfoSchema
import ge.carapp.carappapi.security.AuthenticatedUserProvider
import ge.carapp.carappapi.service.payment.PaymentService
import mu.KotlinLogging
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import reactor.core.publisher.Mono
import java.util.UUID

private val logger = KotlinLogging.logger {}

@Controller
class PaymentController(val paymentService: PaymentService) {

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    suspend fun initPayment(@Argument input: InitializePaymentInput): Mono<OrderProcessingResponse> {
        val authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser()
        logger.info { "Authenticated User: $authenticatedUser, input: $input" }
        return paymentService.createOrder(authenticatedUser, input)
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    suspend fun createOrderBySavedCard(@Argument input: InitializePaymentWithSavedCardsInput): Mono<OrderProcessingResponse> {
        val authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser()
        logger.info { "Authenticated User: $authenticatedUser, input: $input" }
        return paymentService.createOrderBySavedCard(authenticatedUser, input)
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    suspend fun getPaymentInfo(@Argument orderId: UUID): Mono<PaymentInfoSchema> {
        val authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser()
        logger.info { "Authenticated User: $authenticatedUser, input: $orderId" }
        return paymentService.retrievePaymentInfo(authenticatedUser, orderId)
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    suspend fun saveCard(@Argument orderId: UUID): Mono<Boolean> {
        val authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser()
        logger.info { "Authenticated User: $authenticatedUser, input: $orderId" }
        return paymentService.saveCard(authenticatedUser, orderId)
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    suspend fun removeCard(@Argument orderId: UUID): Mono<Boolean> {
        val authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser()
        logger.info { "Authenticated User: $authenticatedUser, input: $orderId" }
        return paymentService.removeCard(authenticatedUser, orderId)
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    suspend fun confirmPreAuthorization(@Argument orderId: UUID): Mono<Boolean> {
        val authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser()
        logger.info { "Authenticated User: $authenticatedUser, input: $orderId" }
        return paymentService.confirmPreAuthorization(authenticatedUser, orderId, null)
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    suspend fun rejectPreAuthorization(@Argument orderId: UUID): Mono<Boolean> {
        val authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser()
        logger.info { "Authenticated User: $authenticatedUser, input: $orderId" }
        return paymentService.rejectPreAuthorization(authenticatedUser, orderId)
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    suspend fun refund(@Argument orderId: UUID, @Argument refundAmount: String): Mono<Boolean> {
        val authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser()
        logger.info { "Authenticated User: $authenticatedUser, input: $orderId" }
        return paymentService.refund(authenticatedUser, orderId, refundAmount)
    }

}

