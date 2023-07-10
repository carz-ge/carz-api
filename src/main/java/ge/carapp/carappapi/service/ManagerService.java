package ge.carapp.carappapi.service;

import ge.carapp.carappapi.entity.ManagerEntity;
import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.exception.GeneralException;
import ge.carapp.carappapi.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository managerRepository;

    public ManagerEntity createManager(UserEntity user, UUID providerId) {
        Optional<ManagerEntity> managerOptional = managerRepository.findByUserId(user.getId());
        if (managerOptional.isPresent())  {
            throw new GeneralException("manager already exists");
        }

        ManagerEntity manager = ManagerEntity.builder()
            .user(user)
            .providerId(providerId)
            .isActive(true)
            .build();
        manager = managerRepository.save(manager);

        return manager;
    }

    public ManagerEntity getManager(UserEntity managerUser) {
        Optional<ManagerEntity> managerOptional = managerRepository.findByUserId(managerUser.getId());
        if (managerOptional.isEmpty()) {
            throw new GeneralException("Manager not found");
        }
        return managerOptional.get();
    }


    public List<ManagerEntity> getAllManagersForProvider(UUID providerId) {
        return managerRepository.findAllByProviderId(providerId);
    }



}
