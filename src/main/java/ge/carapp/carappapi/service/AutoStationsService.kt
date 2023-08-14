package ge.carapp.carappapi.service

import ge.carapp.carappapi.repository.AutoStationsRepository
import ge.carapp.carappapi.schema.AutoStationsSchema
import ge.carapp.carappapi.schema.convert
import mu.KotlinLogging
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class AutoStationsService(private val autoStationsRepository: AutoStationsRepository) {

    @Cacheable("autoStations")
    fun listAllStations(): List<AutoStationsSchema> {
        val stations = autoStationsRepository.findAll().map {
            convert(it)
        }.toList()
        logger.info { "list All stations ${stations.size}" }
        return stations
    }
}
