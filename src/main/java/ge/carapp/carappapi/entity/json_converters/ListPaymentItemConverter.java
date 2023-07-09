package ge.carapp.carappapi.entity.json_converters;

import ge.carapp.carappapi.models.bog.details.Item;

public class ListPaymentItemConverter extends ListConverter<Item>{

    @Override
    protected Class<Item> getTypeParameterClass() {
        return Item.class;
    }
}
