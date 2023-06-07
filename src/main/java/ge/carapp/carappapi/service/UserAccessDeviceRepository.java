package ge.carapp.carappapi.service;

import ge.carapp.carappapi.entity.UserAccessDeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserAccessDeviceRepository extends JpaRepository<UserAccessDeviceEntity, UUID> {
}
