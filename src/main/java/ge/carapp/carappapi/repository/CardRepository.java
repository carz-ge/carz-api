package ge.carapp.carappapi.repository;

import ge.carapp.carappapi.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, UUID> {

    List<CardEntity> findAllByUserIdAndRemoved(UUID userId, boolean removed);

    List<CardEntity> findAllByUserId(UUID userId);

    Optional<CardEntity> findByIdAndUserId(UUID cardId, UUID userId);
}
