package org.nastya.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "lacations",
        indexes = @Index(columnList = "userId, name, latitude, longitude"))
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    private BigDecimal latitude;

    private BigDecimal longitude;
}
