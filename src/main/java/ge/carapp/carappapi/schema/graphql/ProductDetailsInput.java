package ge.carapp.carappapi.schema.graphql;


import ge.carapp.carappapi.schema.Currency;
import ge.carapp.carappapi.schema.LingualString;

import java.util.List;
import java.util.UUID;

public record ProductDetailsInput(UUID productId,

                                  LingualString name,
                                  LingualString description,
                                  List<ProductDetailsCarPriceInput> pricesForCarTypes,

                                  List<LingualString> availableServices,
                                  List<LingualString> notAvailableServices,
                                  Currency currency,
                                  int averageDurationMinutes
) {
}
