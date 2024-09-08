package org.nastya.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table (name = "sessions",
        indexes = @Index(columnList = "userId, expires_at"))
@Data
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
