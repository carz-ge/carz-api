package ge.carapp.carappapi.schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LingualString {
    private String en;
    private String ka;
}
