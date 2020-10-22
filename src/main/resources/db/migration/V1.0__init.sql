CREATE TYPE SEVERITY AS ENUM ('high', 'medium', 'low');
CREATE TYPE STATUS AS ENUM ('backlog', 'paused', 'inProgress', 'inReview', 'done');

CREATE TABLE ORGANIZATION (
    orgId UUID PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE PICTURE (
    pictureId UUID PRIMARY KEY,
    img BYTEA
);

CREATE TABLE USERS (
    userId UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    emailDomain VARCHAR(255),
    emailUser VARCHAR(255),
    organization UUID,
    displayPicture UUID,
    FOREIGN KEY (organization) REFERENCES ORGANIZATION(orgId),
    FOREIGN KEY (displayPicture) REFERENCES PICTURE(pictureId)
);

CREATE TABLE MANAGER (
    userId UUID NOT NULL PRIMARY KEY,
    FOREIGN KEY (userId) REFERENCES USERS(userId)
);

CREATE TABLE DEVELOPER (
    userId UUID NOT NULL PRIMARY KEY,
    FOREIGN KEY (userId) REFERENCES USERS(userId)
);

CREATE TABLE TEAM (
    teamId UUID PRIMARY KEY,
    orgId UUID NOT NULL,
    manager UUID,
    logo BYTEA,
    name VARCHAR(255) NOT NULL,
    FOREIGN KEY (orgId) REFERENCES ORGANIZATION(orgId),
    FOREIGN KEY (manager) REFERENCES MANAGER(userId)
);

CREATE TABLE TEAM_MEMBERS (
    userId UUID NOT NULL,
    teamId UUID NOT NULL,
    PRIMARY KEY (userId, teamId),
    FOREIGN KEY (userId) REFERENCES USERS(userId),
    FOREIGN KEY (teamId) REFERENCES TEAM(teamId)
);

CREATE TABLE SKILL (
    skillId SERIAL PRIMARY KEY,
    skillDescription VARCHAR(255)
);

CREATE TABLE USER_SKILL (
    userId UUID,
    skillId INT,
    PRIMARY KEY (userId, skillId),
    FOREIGN KEY (userId) REFERENCES USERS(userId),
    FOREIGN KEY (skillId) REFERENCES SKILL(skillId)
);

CREATE TABLE SPRINTS (
    sprintNumber INT,
    capacity INT,
    startDate TIMESTAMP NOT NULL,
    endDate TIMESTAMP NOT NULL,
    sprintLoad INT,
    CHECK (startDate < endDate)
);

CREATE TABLE PROJECTS (
    projectId UUID PRIMARY KEY,
    projectName VARCHAR(255),
    createdBy UUID,
    FOREIGN KEY (createdBy) REFERENCES TEAM(teamId)
);

CREATE TABLE TICKETS (
    ticketId UUID NOT NULL PRIMARY KEY,
    ticketTitle VARCHAR(255),
    severity SEVERITY,
    status STATUS,
    projectId UUID,
    dateIssue TIMESTAMP,
    creatorId UUID,
    assigneeId UUID,
    points INT,
    FOREIGN KEY (projectId) REFERENCES PROJECTS(projectId),
    FOREIGN KEY (creatorId) REFERENCES USERS(userId),
    FOREIGN KEY (assigneeId) REFERENCES USERS(userId)
);

CREATE TABLE COMMENTS (
    commentId UUID PRIMARY KEY,
    commentText VARCHAR(255),
    commentDate TIMESTAMP,
    ticketId UUID,
    FOREIGN KEY (ticketId) REFERENCES TICKETS(ticketId)
);