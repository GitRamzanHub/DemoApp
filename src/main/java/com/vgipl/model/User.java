package com.vgipl.model;

import jakarta.persistence.*;
import lombok.Getter;

import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String city;

    private String state;

    private String role = "ADMIN";
}
