package ge.carapp.carappapi.schema;

import ge.carapp.carappapi.entity.ProviderEntity;
import lombok.Builder;

import java.util.UUID;

@Builder
public  record ProviderSchema(
    UUID id,
    String name,
    String phone,
    String email,
    String logo,
    String website
) {
    public static ProviderSchema convert(ProviderEntity providerEntity) {
        return ProviderSchema.builder()
            .id(providerEntity.getId())
            .name(providerEntity.getName())
            .phone(providerEntity.getPhone())
            .logo(providerEntity.getLogo())
            .email(providerEntity.getEmail())
            .website(providerEntity.getWebsite())
            .build();
    }
}
