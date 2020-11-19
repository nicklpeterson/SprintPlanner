package com.cpsc304.sprintplanner.persistence.entities;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USERS", schema= "public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userid")
    private UUID id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column (name = "organization")
    private UUID organization;

    @Column(name = "displaypicture")
    private UUID displayPicture;

    @Column (name = "ismanager")
    private boolean isManager;
}