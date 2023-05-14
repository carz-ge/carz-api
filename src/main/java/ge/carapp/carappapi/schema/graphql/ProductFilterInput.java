package ge.carapp.carappapi.schema.graphql;

import ge.carapp.carappapi.schema.graphql.CarType;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductFilterInput {
    UUID categoryId;
    CarType carType;
    String date;
    String time;
}
