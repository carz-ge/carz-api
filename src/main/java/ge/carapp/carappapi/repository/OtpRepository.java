package ge.carapp.carappapi.repository;

import ge.carapp.carappapi.entity.UserOtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OtpRepository extends JpaRepository<UserOtpEntity, UUID> {
    Optional<UserOtpEntity> findByUserId(UUID userId);
}
