package ge.carapp.carappapi.schema

import ge.carapp.carappapi.entity.AutoStationEntity
import ge.carapp.carappapi.schema.location.AddressSchema
import ge.carapp.carappapi.schema.location.CoordinatesSchema
import ge.carapp.carappapi.schema.location.LocationSchema
import java.util.*

data class AutoStationsSchema(
    val id: UUID,
    val name: LingualString,
    val stationType: String,
    val providerCode: String,

    var description: LingualString? = null,

    val active: Boolean,

    val location: LocationSchema,
    var region: LingualString? = null,
    var image: String? = null,

    var productTypes: String? = null,
    var objectTypes: String? = null,
    var paymentTypes: String? = null,
    var serviceTypes: String? = null,
    var textHtml: String? = null,
    var textHtmlEn: String? = null,
) {


}

fun convert(entity: AutoStationEntity): AutoStationsSchema {
    val addressSchema = AddressSchema(
        entity.address,
        entity.city,
        entity.city
    )
    val addressEnSchema = AddressSchema(
        entity.addressEn,
        entity.cityEn,
        entity.cityEn
    )

    val coordinatesSchema = CoordinatesSchema(entity.latitude, entity.longitude);

    return AutoStationsSchema(
        id = entity.id,
        name = LingualString(entity.name, entity.nameEn),
        stationType = entity.stationType,
        providerCode = entity.providerCode,
        description = LingualString(entity.description, entity.descriptionEn),
        active = entity.active,
        location = LocationSchema(addressSchema, addressEnSchema, coordinatesSchema),
        region = LingualString(entity.region, entity.regionEn),
        image = entity.picture,
        productTypes = Objects.toString(entity.productTypes),
        objectTypes = Objects.toString(entity.objectTypes),
        paymentTypes = Objects.toString(entity.paymentTypes),
        serviceTypes = Objects.toString(entity.serviceTypes),
        textHtml = Objects.toString(entity.textHtml),
        textHtmlEn = Objects.toString(entity.textHtmlEn),


        )
}
