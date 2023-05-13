package ge.carapp.carappapi;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ProductResolver {

    @QueryMapping
    public List<Product> getProducts() {
        return List.of(new Product("id1", "name1"), new Product("id2", "name2"));
    }


}
