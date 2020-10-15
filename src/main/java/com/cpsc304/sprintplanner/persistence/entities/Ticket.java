package com.cpsc304.sprintplanner.persistence.entities;

import com.cpsc304.sprintplanner.persistence.entities.enums.Severity;
import com.cpsc304.sprintplanner.persistence.entities.enums.Status;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

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
    @Column(name = "id")
    private Integer id;

    @Column(name = "ticket_name")
    private String ticketName;

    @Column(name = "creator")
    private String creator;

    @Column(name = "status_change_date")
    private Timestamp statusChangeDate;

    @Column(name = "ticket_description")
    private String ticketDescription;

    @Column(name = "severity")
    private Severity severity;

    @Column(name = "status")
    private Status status;

    @Column(name = "dateIssue")
    private Timestamp dateIssue;
}
