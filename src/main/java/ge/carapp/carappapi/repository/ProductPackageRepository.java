package ge.carapp.carappapi.repository;

import ge.carapp.carappapi.entity.ProductPackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductPackageRepository extends JpaRepository<ProductPackageEntity, UUID> {

    List<ProductPackageEntity> findAllByProductId(UUID productId);
    List<ProductPackageEntity> findAllByProductIdIn(List<UUID> productIds);
 }
