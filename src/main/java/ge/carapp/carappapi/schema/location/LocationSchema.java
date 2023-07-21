package ge.carapp.carappapi.schema.location;

public record LocationSchema(AddressSchema address,
                             AddressSchema addressEn,
                             CoordinatesSchema coordinates) {
}
