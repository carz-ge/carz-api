package ge.carapp.carappapi.schema;

import ge.carapp.carappapi.entity.ReviewEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record ReviewSchema(
    UUID id,
    @NotNull UUID productId,
    int stars,
    String comment,
    LocalDateTime createdAt
) {

    public static ReviewSchema convert(ReviewEntity reviewEntity) {
        return ReviewSchema.builder()
            .id(reviewEntity.getId())
            .productId(reviewEntity.getProductId())
            .stars(reviewEntity.getStars())
            .comment(reviewEntity.getComment())
            .createdAt(reviewEntity.getCreatedAt())
            .build();
    }
}
