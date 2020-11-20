package com.cpsc304.sprintplanner.persistence.entities;

import lombok.*;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SPRINTS", schema="public")
public class Sprint {
    @Id
    @Column(name="sprintnumber")
    Integer sprintNumber;

    @Column(name="capacity")
    Integer capacity;

    @Column(name="startdate")
    Timestamp startDate;

    @Column(name="enddate")
    Timestamp endDate;

    @Column(name="sprintload")
    Integer sprintLoad;

    @Column(name="belongsto")
    UUID belongsTo;

    @Transient
    String projectName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sprint sprint = (Sprint) o;
        return Objects.equals(sprintNumber, sprint.sprintNumber) &&
                Objects.equals(capacity, sprint.capacity) &&
                Objects.equals(startDate, sprint.startDate) &&
                Objects.equals(endDate, sprint.endDate) &&
                Objects.equals(sprintLoad, sprint.sprintLoad) &&
                Objects.equals(belongsTo, sprint.belongsTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sprintNumber, capacity, startDate, endDate, sprintLoad, belongsTo);
    }
}
