package ge.carapp.carappapi.repository;

import ge.carapp.carappapi.entity.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatRepository extends JpaRepository<ChatMessageEntity, UUID> {
    List<ChatMessageEntity> findAllByUserId(UUID userId);

    List<ChatMessageEntity> findTop10ByUserIdOrderByIdDesc(UUID userId);
}
