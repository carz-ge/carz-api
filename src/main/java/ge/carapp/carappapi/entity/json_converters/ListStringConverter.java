package ge.carapp.carappapi.entity.json_converters;

public class ListStringConverter extends ListConverter<String> {
    @Override
    protected Class<String> getTypeParameterClass() {
        return String.class;
    }
}
