package ge.carapp.carappapi.entity

import jakarta.persistence.*
import lombok.*
import java.time.LocalDateTime
import java.util.*

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
@Entity
@Table(name = "auto_stations")
data class AutoStationEntity(
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    var id: UUID,

    @Column(name = "id_by_provider", nullable = false)
    var idByProvider: String,

    @Column(name = "station_type", nullable = false)
    var stationType: String,

    @Column(name = "provider_code", nullable = false)
    var providerCode: String,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "name_en", nullable = false)
    var nameEn: String,
    var description: String? = null,
    var descriptionEn: String? = null,

    @Column(name = "active")
    var active: Boolean,

    var latitude: Double,

    var longitude: Double,
    var region: String? = null,
    var regionEn: String? = null,
    var city: String? = null,
    var cityEn: String? = null,
    var address: String? = null,
    var addressEn: String? = null,
    var picture: String? = null,
    var productTypes: Any? = null,
    var objectTypes: Any? = null,
    var paymentTypes: Any? = null,
    var serviceTypes: Any? = null,
    var textHtml: ByteArray? = null,
    var textHtmlEn: ByteArray? = null,
    var createdAt: LocalDateTime? = null,
    var updatedAt: LocalDateTime? = null,
    var deletedAt: LocalDateTime? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AutoStationEntity

        if (id != other.id) return false
        if (idByProvider != other.idByProvider) return false
        if (stationType != other.stationType) return false
        if (providerCode != other.providerCode) return false
        if (name != other.name) return false
        if (nameEn != other.nameEn) return false
        if (description != other.description) return false
        if (descriptionEn != other.descriptionEn) return false
        if (active != other.active) return false
        if (latitude != other.latitude) return false
        if (longitude != other.longitude) return false
        if (region != other.region) return false
        if (regionEn != other.regionEn) return false
        if (city != other.city) return false
        if (cityEn != other.cityEn) return false
        if (address != other.address) return false
        if (addressEn != other.addressEn) return false
        if (picture != other.picture) return false
        if (productTypes != other.productTypes) return false
        if (objectTypes != other.objectTypes) return false
        if (paymentTypes != other.paymentTypes) return false
        if (serviceTypes != other.serviceTypes) return false
        if (createdAt != other.createdAt) return false
        if (updatedAt != other.updatedAt) return false
        return deletedAt == other.deletedAt
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + idByProvider.hashCode()
        result = 31 * result + stationType.hashCode()
        result = 31 * result + providerCode.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + nameEn.hashCode()
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (descriptionEn?.hashCode() ?: 0)
        result = 31 * result + active.hashCode()
        result = 31 * result + latitude.hashCode()
        result = 31 * result + longitude.hashCode()
        result = 31 * result + (region?.hashCode() ?: 0)
        result = 31 * result + (regionEn?.hashCode() ?: 0)
        result = 31 * result + (city?.hashCode() ?: 0)
        result = 31 * result + (cityEn?.hashCode() ?: 0)
        result = 31 * result + (address?.hashCode() ?: 0)
        result = 31 * result + (addressEn?.hashCode() ?: 0)
        result = 31 * result + (picture?.hashCode() ?: 0)
        result = 31 * result + (productTypes?.hashCode() ?: 0)
        result = 31 * result + (objectTypes?.hashCode() ?: 0)
        result = 31 * result + (paymentTypes?.hashCode() ?: 0)
        result = 31 * result + (serviceTypes?.hashCode() ?: 0)
        result = 31 * result + (createdAt?.hashCode() ?: 0)
        result = 31 * result + (updatedAt?.hashCode() ?: 0)
        result = 31 * result + (deletedAt?.hashCode() ?: 0)
        return result
    }

}
