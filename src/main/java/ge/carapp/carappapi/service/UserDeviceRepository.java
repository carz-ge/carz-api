package ge.carapp.carappapi.service;

import ge.carapp.carappapi.entity.UserDeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserDeviceRepository extends JpaRepository<UserDeviceEntity, UUID> {
}
