package ge.carapp.carappapi.controller;


import ge.carapp.carappapi.schema.ProductSchema;
import ge.carapp.carappapi.schema.graphql.ProductFilterInput;
import ge.carapp.carappapi.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

@Controller
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public Flux<ProductSchema> searchProducts(@Argument ProductFilterInput filter) {
        return searchService.searchProducts(filter);
    }
}
