package org.nastya.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Session {
    @Id
    @Column(name = "session_id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    @Column(name = "expires_at")
    private Date expiresAt;
}
