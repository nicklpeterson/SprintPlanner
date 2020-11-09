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
@Table(name = "TEAM", schema="public")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "projectid")
    private UUID teamId;

    @Column(name = "orgid")
    private UUID orgId;

    @Column(name="manager")
    private UUID manager;

    @Column(name="log")
    private byte[] logo;

    @Column(name="name")
    String name;
}
