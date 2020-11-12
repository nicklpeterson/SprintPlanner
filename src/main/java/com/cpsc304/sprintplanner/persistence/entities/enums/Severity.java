package com.cpsc304.sprintplanner.persistence.entities.enums;

public enum Severity {
    high("high"),
    medium("medium"),
    low("low");

    public final String severity;
    Severity(String severity) {
        this.severity = severity;
    }
}
