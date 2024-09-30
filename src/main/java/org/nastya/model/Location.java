package org.nastya.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "locations",
        indexes = {
                @Index(name = "idx_user", columnList = "user_id"),
                @Index(name = "idx_name", columnList = "name"),
                @Index(name = "idx_latitude", columnList = "latitude"),
                @Index(name = "idx_longitude", columnList = "longitude")},
        uniqueConstraints = {
                @UniqueConstraint(name = "UniqueNameAndUser", columnNames = {"name", "user_id"})
        })
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private BigDecimal latitude;

    private BigDecimal longitude;

    public Location(String name, User user, BigDecimal latitude, BigDecimal longitude) {
        this.name = name;
        this.user = user;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
