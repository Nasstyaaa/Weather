package org.nastya.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Session {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    private Date expiresAt;
}
