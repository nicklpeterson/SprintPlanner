package com.cpsc304.sprintplanner.dto;

import com.cpsc304.sprintplanner.persistence.entities.enums.Severity;
import com.cpsc304.sprintplanner.persistence.entities.enums.Status;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class TicketDto {
    private String ticketTitle;

    private Severity severity;

    private Status status;

    private int sprintNumber;

    private Timestamp dateIssue;

    private UUID creatorId;

    private UUID assigneeId;

    private UUID projectId;

    private int points;
}