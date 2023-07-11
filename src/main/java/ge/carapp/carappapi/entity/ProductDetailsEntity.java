package ge.carapp.carappapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ge.carapp.carappapi.entity.json_converters.ListLingualStringConverter;
import ge.carapp.carappapi.entity.json_converters.ListProductDetailsCarPriceConverter;
import ge.carapp.carappapi.schema.Currency;
import ge.carapp.carappapi.schema.LingualString;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "PRODUCT_DETAILS")
public class ProductDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private ProductEntity product;

    private String name;
    private String nameKa;
    private String description;
    private String descriptionKa;

    @Convert(converter = ListProductDetailsCarPriceConverter.class)
    private List<ProductDetailsCarPrice> pricesForCarTypes;
    @Convert(converter = ListLingualStringConverter.class)

    private List<LingualString> availableServices;
    @Convert(converter = ListLingualStringConverter.class)
    private List<LingualString> notAvailableServices;
    @Enumerated(EnumType.STRING)
    private Currency currency;

//    @Column(name = "AVERAGE_DURATION_MINUTES");
    private Integer averageDurationMinutes;
}
