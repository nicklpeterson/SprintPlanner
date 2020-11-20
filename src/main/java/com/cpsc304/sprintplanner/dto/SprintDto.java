package com.cpsc304.sprintplanner.dto;

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
public class SprintDto {
    private int sprintNumber;

    private int capacity;

    private Timestamp startDate;

    private Timestamp endDate;

    private UUID belongsTo;

    private int sprintLoad;

}
