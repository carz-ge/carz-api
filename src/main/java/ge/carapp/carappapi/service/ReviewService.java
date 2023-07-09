package ge.carapp.carappapi.service;

import ge.carapp.carappapi.entity.ProductEntity;
import ge.carapp.carappapi.entity.ReviewEntity;
import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.repository.ReviewRepository;
import ge.carapp.carappapi.schema.ReviewSchema;
import ge.carapp.carappapi.schema.graphql.AddReviewInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductService productService;

    public List<ReviewSchema> listProductReviews(UUID productId) {
        return reviewRepository.findAllByProductId(productId)
            .stream().map(ReviewSchema::convert).toList();
    }

    public ReviewSchema addProductReview(UserEntity user, AddReviewInput input) {
        ProductEntity productEntity = productService.getProductEntity(input.productId());

        ReviewEntity reviewEntity = ReviewEntity.builder()
            .userId(user.getId())
            .comment(input.comment())
            .stars(input.stars())
            .show(true)
            .priority(0)
            .build();

        reviewEntity = reviewRepository.save(reviewEntity);

        productService.updateReviewCount(productEntity, input.stars());


        return null;
    }
}
