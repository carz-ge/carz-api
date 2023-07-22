package ge.carapp.carappapi.controller

import ge.carapp.carappapi.schema.AutoStationsSchema
import ge.carapp.carappapi.security.AuthenticatedUserProvider
import ge.carapp.carappapi.service.AutoStationsService
import mu.KotlinLogging
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller

private val logger = KotlinLogging.logger {}

@Controller
class AutoStationsController(val autoStationsService: AutoStationsService) {

    @QueryMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    suspend fun listAutoStations(): List<AutoStationsSchema> {
        val authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser()
        return autoStationsService.listAllStations()
    }
}
