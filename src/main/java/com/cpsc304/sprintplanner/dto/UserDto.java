package com.cpsc304.sprintplanner.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class UserDto {
    private String username;
    private String password;
    private String email;
    private String organizationName;
    private UUID organizationUUID;
    private UUID displayPictureUUID;
    private Boolean isManager;
}
