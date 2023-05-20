package ge.carapp.carappapi.service;

import ge.carapp.carappapi.entity.ProviderEntity;
import ge.carapp.carappapi.exception.GeneralException;
import ge.carapp.carappapi.repository.ProviderRepository;
import ge.carapp.carappapi.schema.ProviderSchema;
import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.schema.graphql.ProviderInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProviderService {
    private final ProviderRepository providerRepository;

    public List<ProviderSchema> getProviders() {
        return providerRepository.findAll().stream().map(ProviderSchema::convert).toList();
    }

    public ProviderSchema createProvider(UserEntity authenticatedUser, ProviderInput input) {
        if (providerRepository.findByName(input.name()).isPresent()) {
            throw new GeneralException("Provider name already exists");
        }

        ProviderEntity provider = ProviderEntity.builder()
            .name(input.name())
            .phone(input.phone())
            .logo(input.logo())
            .email(input.email())
            .website(input.website())
            .build();

        providerRepository.save(provider);

        return ProviderSchema.convert(provider);
    }

    public ProviderSchema updateProvider(UserEntity authenticatedUser, UUID categoryId, ProviderInput input) {

        Optional<ProviderEntity> providerOptional = providerRepository.findById(categoryId);
        if (providerOptional.isEmpty()) {
            throw new GeneralException("Provider not found");
        }

        ProviderEntity provider = providerOptional.get();

        provider.setName(input.name());
        provider.setPhone(input.phone());
        provider.setLogo(input.logo());
        provider.setEmail(input.email());
        provider.setWebsite(input.website());

        providerRepository.save(provider);

        return ProviderSchema.convert(provider);
    }

    public boolean removeProvider(UserEntity authenticatedUser, UUID providerId) {
        try {
            providerRepository.deleteById(providerId);
            return true;
        } catch (Exception e) {
            log.error("could not delete provider", e);
            return false;
        }
    }
}
