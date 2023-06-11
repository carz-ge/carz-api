package ge.carapp.carappapi.repository;

import ge.carapp.carappapi.entity.ProductEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    List<ProductEntity> findAllByCategoryId(@NotNull UUID categoryId);

    List<ProductEntity> findAllByProviderId(@NotNull UUID providerId);

}
