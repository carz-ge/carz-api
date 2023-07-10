package ge.carapp.carappapi.repository;

import ge.carapp.carappapi.entity.ManagerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ManagerRepository extends JpaRepository<ManagerEntity, UUID> {

    Optional<ManagerEntity> findByUserId(UUID userId);


    List<ManagerEntity> findAllByProviderId(UUID providerId);

}
