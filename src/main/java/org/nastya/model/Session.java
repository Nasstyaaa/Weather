package org.nastya.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table (name = "sessions",
        indexes = {
                @Index(name = "idx_user_id", columnList = "user_id"),
                @Index(name = "idx_expires", columnList = "expires_at")})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Session {
    @Id
    @Column(name = "session_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
}
