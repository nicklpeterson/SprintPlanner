package com.cpsc304.sprintplanner.persistence.entities;

import com.cpsc304.sprintplanner.persistence.entities.enums.PostgreSQLEnumType;
import com.cpsc304.sprintplanner.persistence.entities.enums.Severity;
import com.cpsc304.sprintplanner.persistence.entities.enums.Status;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TICKETS", schema="public")
@TypeDef(
        name = "pgsql_enum",
        typeClass = PostgreSQLEnumType.class
)
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ticketid")
    private UUID ticketId;

    @Column(name = "tickettitle")
    private String ticketTitle;

    @Column(name = "creatorid")
    private UUID creatorId;

    @Column(name = "projectid")
    private UUID projectId;

    @Column(name = "sprintnumber")
    private Integer sprintNumber;

    @Column(name = "assigneeid")
    private UUID assigneeId;

    @Column(name = "points")
    private Integer points;

    // followed: https://stackoverflow.com/questions/27804069/hibernate-mapping-between-postgresql-enum-and-java-enum/27807765
    // in order to use enum type with postgres
    @Enumerated(EnumType.STRING)
    @Type( type = "pgsql_enum" )
    @Column(name = "severity")
    private Severity severity;

    // followed: https://stackoverflow.com/questions/27804069/hibernate-mapping-between-postgresql-enum-and-java-enum/27807765
    // in order to use enum type with postgres
    @Enumerated(EnumType.STRING)
    @Type( type = "pgsql_enum" )
    @Column(name = "status")
    private Status status;

    @Column(name = "dateissue")
    private Timestamp dateIssue;
}