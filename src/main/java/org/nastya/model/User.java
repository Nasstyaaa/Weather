package org.nastya.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "users",
        indexes = @Index(name = "idx_password", columnList = "password"))
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String login;

    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
