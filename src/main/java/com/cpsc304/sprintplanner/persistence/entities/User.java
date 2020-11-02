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
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userId")
    private UUID id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column (name = "emailDomain")
    private String emailDomain;

    @Column (name = "emailUser")
    private String emailUser;

    @Column (name = "organization")
    private UUID organization;

    @Column(name = "displayPicture")
    private UUID displayPicture;
}