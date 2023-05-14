package ge.carapp.carappapi.entity;

import ge.carapp.carappapi.entity.json_converters.ListStringConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "PRODUCT")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    @JoinColumn(name = "NAME_KA")
    private String nameKa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private CategoryEntity category;

    private String description;
    @JoinColumn(name = "DESCRIPTION_KA")
    private String descriptionKa;

    // TODO
//    location: LocationInput
    private String mainImage;
    @Convert(converter = ListStringConverter.class)
    private List<String> images;
    @Convert(converter = ListStringConverter.class)
    private List<String> tags;

//    reviewRate: Float
//    reviewCount: Int
}
