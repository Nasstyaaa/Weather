package org.nastya.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "users",
        indexes = @Index(columnList = "login, password"))
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String login;

    private String password;
}
