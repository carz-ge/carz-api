package ge.carapp.carappapi.schema.graphql;


import ge.carapp.carappapi.schema.Currency;
import ge.carapp.carappapi.schema.LingualString;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record ProductDetailsInput(@NotNull UUID productId,

                                  @NotNull LingualString name,
                                  LingualString description,
                                  List<ProductDetailsCarPriceInput> pricesForCarTypes,

                                  List<LingualString> availableServices,
                                  List<LingualString> notAvailableServices,
                                  Currency currency,
                                  int averageDurationMinutes
) {
}
