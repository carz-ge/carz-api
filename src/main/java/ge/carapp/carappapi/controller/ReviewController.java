package ge.carapp.carappapi.controller;

import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.schema.ReviewSchema;
import ge.carapp.carappapi.schema.graphql.AddReviewInput;
import ge.carapp.carappapi.security.AuthenticatedUserProvider;
import ge.carapp.carappapi.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Validated
public class ReviewController {
    private final ReviewService reviewService;

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public List<ReviewSchema> listProductReviews(@Argument UUID productId) {
        return reviewService.listProductReviews(productId);
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ReviewSchema addProductReview(@Argument AddReviewInput input) {
        UserEntity authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser();
        return reviewService.addProductReview(authenticatedUser, input);
    }
}
