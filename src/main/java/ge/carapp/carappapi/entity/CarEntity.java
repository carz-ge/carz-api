package ge.carapp.carappapi.entity;

import ge.carapp.carappapi.schema.graphql.CarType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "CAR")
public class CarEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "PLATE_NUMBER")
    private String plateNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "CAR_TYPE")
    private CarType carType;

    @Column(name = "TECH_PASS_NUMBER")
    String techPassportNumber;
    String vin;
    String make;
    String model;
    int year;


    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;


    @ManyToOne(optional = false)
    private UserEntity owner;

}
