package ge.carapp.carappapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "USER_DEVICE")
public class UserDeviceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    private UserEntity user;

    @Column(nullable = false)
    private String deviceToken;

    private String platform;
}
