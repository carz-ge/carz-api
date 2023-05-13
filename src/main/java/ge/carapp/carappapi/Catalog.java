package ge.carapp.carappapi;

import lombok.Data;

import java.util.List;

@Data
public class Catalog {

    private String id;

    private String name;

    private List<Product> products;

    public Catalog(String id, String name) {
        this.id = id;
        this.name = name;
    }

}
