package ge.carapp.carappapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ge.carapp.carappapi.entity.json_converters.ListStringConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
@Entity
@Table(name = "PRODUCT")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    @JoinColumn(name = "NAME_KA")
    private String nameKa;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    private CategoryEntity category;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROVIDER_ID", nullable = false)
    private ProviderEntity provider;

    private String description;
    @Column(name = "DESCRIPTION_KA")
    private String descriptionKa;


    private String mainImage;
    @Convert(converter = ListStringConverter.class)
    private List<String> images;

    @Convert(converter = ListStringConverter.class)
    private List<String> tags;

    // address
    private String street;
    private String district;
    private String city;
    private String streetEn;
    private String districtEn;
    private String cityEn;
    // coordinates
    private double lat;
    private double lng;

    private int capacity;

    private Integer reviewStars;
    private Integer totalReviews;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID")
    private List<ProductPackageEntity> productDetailsList;

}
