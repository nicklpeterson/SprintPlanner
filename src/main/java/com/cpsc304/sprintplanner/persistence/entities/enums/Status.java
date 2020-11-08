package com.cpsc304.sprintplanner.persistence.entities.enums;

public enum Status {
    backlog("backlog"),
    paused("paused"),
    inProgress("in progress"),
    inReview("in review"),
    done("done");

    public final String status;

    Status(String status) {
        this.status = status;
    }
}