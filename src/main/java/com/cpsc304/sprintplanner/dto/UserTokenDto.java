package com.cpsc304.sprintplanner.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class UserTokenDto {
    private UUID id;
    private String username;
    private String password;
    private String email;
    private UUID organization;
    private UUID displayPicture;
}