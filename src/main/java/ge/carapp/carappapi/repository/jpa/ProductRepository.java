package ge.carapp.carappapi.repository.jpa;

import ge.carapp.carappapi.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    List<ProductEntity> findAllByCategoryId(UUID categoryId);
    List<ProductEntity> findAllByProviderId(UUID providerId);

}
