package ge.carapp.carappapi.entity.json_converters;

import ge.carapp.carappapi.schema.LingualString;

public class ListLingualStringConverter extends ListConverter<LingualString> {
    @Override
    protected Class<LingualString> getTypeParameterClass() {
        return LingualString.class;
    }
}
