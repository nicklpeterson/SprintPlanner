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
@Table(name = "DISPLAY_PICTURE", schema="public")
public class DisplayPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "img")
    private byte[] img;
}
