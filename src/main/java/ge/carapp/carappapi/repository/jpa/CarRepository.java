package ge.carapp.carappapi.repository.jpa;

import ge.carapp.carappapi.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, UUID> {

    List<CarEntity> findAllByOwnerId(UUID ownerId);
}
