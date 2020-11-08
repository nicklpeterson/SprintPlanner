package com.cpsc304.sprintplanner.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class TicketDto {
    private int id;

    private String ticketName;

    private String creator;
}