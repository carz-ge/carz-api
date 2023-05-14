package ge.carapp.carappapi.schema.location;

import lombok.Data;

@Data
public class LocationSchema {
    Address address;
    Coordinates coordinates;
}
