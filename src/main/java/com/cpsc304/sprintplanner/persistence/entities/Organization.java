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
@Table(name = "ORGANIZATION", schema="public")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "orgid")
    private UUID id;

    @Column(name = "name")
    private String name;
}