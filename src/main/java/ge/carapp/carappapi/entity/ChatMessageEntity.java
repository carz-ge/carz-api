package ge.carapp.carappapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ge.carapp.carappapi.schema.ChatMessageStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
@Entity
@Table(name = "CHAT")
public class ChatMessageEntity {
    @Id
    private String id;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    private UserEntity user;

    private LocalDateTime createdAt;
    private String text;

    @Enumerated(EnumType.STRING)
    private ChatMessageStatus statusChatMessageStatus;

    private Boolean isAnswer;
    private LocalDateTime finishedAt;

    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now();
    }

}
