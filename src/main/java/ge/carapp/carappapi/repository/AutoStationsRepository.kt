package ge.carapp.carappapi.repository;

import ge.carapp.carappapi.entity.AutoStationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AutoStationsRepository : JpaRepository<AutoStationEntity, UUID> {
}
