package ge.carapp.carappapi.repository;

import ge.carapp.carappapi.entity.ProductEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    List<ProductEntity> findAllByCategoryId(@NotNull UUID categoryId);

    List<ProductEntity> findAllByProviderId(@NotNull UUID providerId);

    @Query("update ProductEntity p set p.reviewStars = p.reviewStars + :stars ,  p.totalReviews = p.totalReviews + 1" +
        " where p.id = :productId ")
    @Modifying
    void updateReviews(@Param("productId") UUID productId, @Param("stars") int stars);
}
