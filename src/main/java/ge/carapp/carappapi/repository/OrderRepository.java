package ge.carapp.carappapi.repository;

import ge.carapp.carappapi.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, UUID> {
    Optional<OrderEntity> findByIdempotencyKey(String key);

}

