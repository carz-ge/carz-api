package ge.carapp.carappapi.repository;

import ge.carapp.carappapi.entity.ProductDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductDetailsRepository extends JpaRepository<ProductDetailsEntity, UUID> {

    List<ProductDetailsEntity> findAllByProductId(UUID productId);
 }
