package com.cpsc304.sprintplanner.persistence.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "EMPLOYER")
public class Employer {

    @Id
    @Column(name = "emailDomain")
    private String emailDomain;

    @Column(name = "employerName")
    private String employerName;
}
