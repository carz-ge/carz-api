package ge.carapp.carappapi.entity;

import ge.carapp.carappapi.core.Language;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "USERS") // TODO check serializable or transparent
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String phone;

    private String email;

    private String firstname;
    private String lastname;

    @Enumerated(EnumType.STRING)
    private Language language;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_ROLE", nullable = false)
    private UserRole userRole;

    private Boolean deactivated;
    private Boolean removed;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;

    @ToString.Exclude
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserOtpEntity userOtp;

//    @OneToMany(fetch = FetchType.LAZY,
////            cascade = CascadeType.ALL,
////            orphanRemoval = true,
//            mappedBy = "owner"
//    )
////    @JoinColumn(name = "OWNER_ID", referencedColumnName = "ID")
//    private List<CarEntity> carList = new ArrayList<>();

    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    private void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
