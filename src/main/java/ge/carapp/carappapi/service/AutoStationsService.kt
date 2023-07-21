package ge.carapp.carappapi.service

import ge.carapp.carappapi.repository.AutoStationsRepository
import ge.carapp.carappapi.schema.AutoStationsSchema
import ge.carapp.carappapi.schema.convert
import org.springframework.stereotype.Service

@Service
class AutoStationsService(private val autoStationsRepository: AutoStationsRepository) {

    fun listAllStations(): List<AutoStationsSchema> {
        return autoStationsRepository.findAll().stream().map {
            convert(it)
        }.toList()
    }
}
