CREATE TYPE SEVERITY AS ENUM ('high', 'medium', 'low');
CREATE TYPE STATUS AS ENUM ('backlog', 'paused', 'inProgress', 'inReview', 'done');

CREATE TABLE ORGANIZATION (
    orgId UUID PRIMARY KEY,
    name VARCHAR
);

CREATE TABLE PICTURE (
    pictureId UUID PRIMARY KEY,
    img BYTEA
);

CREATE TABLE EMPLOYER (
    emailDomain VARCHAR PRIMARY KEY
);

CREATE TABLE USERS (
    userId UUID PRIMARY KEY,
    username VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    emailDomain VARCHAR,
    emailUser VARCHAR,
    organization UUID,
    displayPicture UUID,
    FOREIGN KEY (organization) REFERENCES ORGANIZATION(orgId),
    FOREIGN KEY (displayPicture) REFERENCES PICTURE(pictureId),
    FOREIGN KEY (emailDomain) REFERENCES EMPLOYER(emailDomain)
);

CREATE TABLE TEAM (
    teamId UUID PRIMARY KEY,
    orgId UUID NOT NULL,
    logo BYTEA,
    name VARCHAR NOT NULL,
    FOREIGN KEY (orgId) REFERENCES ORGANIZATION(orgId)
);

CREATE TABLE MANAGER (
 	userId UUID NOT NULL PRIMARY KEY,
 	manages UUID,
 	FOREIGN KEY (userId) REFERENCES USERS(userId),
    FOREIGN KEY (manages) REFERENCES TEAM(teamId)
);


CREATE TABLE SKILL (
    skillId SERIAL PRIMARY KEY,
    skillDescription VARCHAR
);

CREATE TABLE DEVELOPER (
    userId UUID NOT NULL,
    skillId INT,
    PRIMARY KEY (userId, skillId),
    FOREIGN KEY (userId) REFERENCES USERS(userId),
    FOREIGN KEY (skillId) REFERENCES SKILL(skillId)
);

CREATE TABLE TEAM_MEMBERS (
    userId UUID NOT NULL,
    teamId UUID NOT NULL,
    PRIMARY KEY (userId, teamId),
    FOREIGN KEY (userId) REFERENCES USERS(userId),
    FOREIGN KEY (teamId) REFERENCES TEAM(teamId)
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
    projectName VARCHAR,
    createdBy UUID,
    FOREIGN KEY (createdBy) REFERENCES TEAM(teamId)
);

CREATE TABLE TICKETS (
    ticketId UUID NOT NULL PRIMARY KEY,
    ticketTitle VARCHAR,
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
    commentText VARCHAR,
    commentDate TIMESTAMP,
    ticketId UUID,
    FOREIGN KEY (ticketId) REFERENCES TICKETS(ticketId)
);