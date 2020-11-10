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
@Table(name = "PROJECTS", schema="public")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "projectId")
    private UUID projectId;

    @Column(name="projectname")
    private String projectName;

    @Column(name="createdBy")
    private UUID createdBy;
}
