package ge.carapp.carappapi.entity.json_converters;

import ge.carapp.carappapi.entity.ProductDetailsCarPrice;

public class ListProductDetailsCarPriceConverter extends ListConverter<ProductDetailsCarPrice>{
    @Override
    protected Class<ProductDetailsCarPrice> getTypeParameterClass() {
        return ProductDetailsCarPrice.class;
    }
}
