package com.cpsc304.sprintplanner.persistence.entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TEAM", schema="public")
public class Team {
    @ManyToMany
    @JoinTable(name="TEAM_MEMBERS",
            joinColumns = { @JoinColumn(name = "teamid") },
            inverseJoinColumns = { @JoinColumn(name = "userid") })
    Set<User> users = new HashSet<>();

    @ManyToOne
    @JoinTable(name="ORGANIZATION", joinColumns = { @JoinColumn(name = "orgid") })
    Organization organization;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "teamid")
    private UUID teamId;

    @Column(name = "orgid")
    private UUID orgId;

    @Column(name="logo")
    private byte[] logo;

    @Column(name="name")
    String name;
}
