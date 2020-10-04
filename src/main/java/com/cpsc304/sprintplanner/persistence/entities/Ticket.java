package com.cpsc304.sprintplanner.persistence.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode

@Entity
@Table(name = "TICKETS")
public class Ticket {
    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TICKET_NAME")
    private String ticketName;

    @Column(name = "CREATOR")
    private String creator;
}
