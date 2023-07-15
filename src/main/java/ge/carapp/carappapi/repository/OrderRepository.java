package ge.carapp.carappapi.repository;

import ge.carapp.carappapi.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    Optional<OrderEntity> findByIdempotencyKey(String key);

    List<OrderEntity> findAllByUserId(UUID userId);
    List<OrderEntity> findAllByProviderId(UUID provider);

}

