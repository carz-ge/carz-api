package ge.carapp.carappapi.schema.location;

public record LocationSchema(AddressSchema address,
                             CoordinatesSchema coordinates) {
}
