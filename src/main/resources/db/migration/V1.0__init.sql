CREATE EXTENSION IF NOT EXISTS "uuid-ossp"; /* Used for generating uuid's */
CREATE TYPE SEVERITY AS ENUM ('high', 'medium', 'low');
CREATE TYPE STATUS AS ENUM ('backlog', 'paused', 'inProgress', 'inReview', 'done');

CREATE TABLE ORGANIZATION (
                              orgId UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                              name VARCHAR UNIQUE
);

CREATE TABLE PICTURE (
                         pictureId UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                         img BYTEA
);

CREATE TABLE USERS (
                       userId UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                       username VARCHAR NOT NULL UNIQUE,
                       password VARCHAR NOT NULL,
                       email VARCHAR,
                       organization UUID NOT NULL,
                       displayPicture UUID DEFAULT uuid_nil(),
                       isManager BOOLEAN NOT NULL DEFAULT false,
                       FOREIGN KEY (organization) REFERENCES ORGANIZATION(orgId),
                       FOREIGN KEY (displayPicture) REFERENCES PICTURE(pictureId)
);

CREATE TABLE TEAM (
                      teamId UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                      orgId UUID NOT NULL,
                      logo BYTEA,
                      name VARCHAR NOT NULL,
                      FOREIGN KEY (orgId) REFERENCES ORGANIZATION(orgId),
                      UNIQUE (orgid, name)
);

CREATE TABLE MANAGER (
                         userId UUID NOT NULL,
                         manages UUID,
                         FOREIGN KEY (userId) REFERENCES USERS(userId),
                         FOREIGN KEY (manages) REFERENCES TEAM(teamId),
                         PRIMARY KEY (userId, manages)
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
                            skillDescription VARCHAR,
                            PRIMARY KEY (userId, skillDescription),
                            FOREIGN KEY (userId) REFERENCES USERS(userId)
);

CREATE TABLE PROJECTS (
                          projectId UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                          projectName VARCHAR,
                          createdBy UUID,
                          FOREIGN KEY (createdBy) REFERENCES TEAM(teamId)
);

CREATE TABLE SPRINTS (
                         sprintNumber INT,
                         capacity INT,
                         startDate TIMESTAMP NOT NULL,
                         endDate TIMESTAMP NOT NULL,
                         belongsTo UUID,
                         sprintLoad INT,
                         FOREIGN KEY (belongsTo) REFERENCES PROJECTS(projectId),
                         CHECK (startDate < endDate),
                         PRIMARY KEY (sprintNumber, belongsTo)
);

CREATE TABLE TICKETS (
                         ticketId UUID NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
                         ticketTitle VARCHAR,
                         severity SEVERITY,
                         status STATUS,
                         projectId UUID,
                         sprintNumber INT,
                         dateIssue TIMESTAMP,
                         creatorId UUID,
                         assigneeId UUID,
                         points INT,
                         FOREIGN KEY (sprintNumber, projectId) REFERENCES SPRINTS(sprintNumber, belongsTo) ON DELETE CASCADE,
                         description VARCHAR,
                         FOREIGN KEY (projectId) REFERENCES PROJECTS(projectId),
                         FOREIGN KEY (creatorId) REFERENCES USERS(userId),
                         FOREIGN KEY (assigneeId) REFERENCES USERS(userId));

CREATE TABLE COMMENTS (
                          commentId UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                          commentText VARCHAR,
                          commentDate TIMESTAMP,
                          ticketId UUID,
                          FOREIGN KEY (ticketId) REFERENCES TICKETS(ticketId) ON DELETE CASCADE
);

INSERT INTO PICTURE (pictureId, img) VALUES (uuid_nil(), decode('iVBORw0KGgoAAAANSUhEUgAAAOIAAADfCAMAAADcKv+WAAAAFVBMVEX29vbm5ubs7Ozo6Oj09PTr6+vw8PAPpVovAAAEoklEQVR4nO2d0ZajMAxDp0D5/09eUjYHaElLEgvLTu7DPKNjWXbSlvn763Q6nU6n0+l0zpnmhSH8mbQfBcA8jONjzzgOs/ZDyTHNR3U7nT7KOT8T+qJK7QesJVnAHaZFXhFoWuR0UaBduw6XBQae2o+bT0YJjbp1zhW4MGg/dBbfB0WKUfuxM8g2adRoZhMoVbhgQ+NULtCKxooaGtFYqdCAxmqF9Lmat9Kcw73olEz8T5h3gLow3SDe5eob8T/aQpJINOIKaztK2TRAalUxmz5YJ4dMmkYoyyiqkDJx5LJmhXA4Cisk3FWli0hYRnGFdN0oG6crZKEqORMjXLNRcrHZoCpj2aXiL6g2VYhCqsBBhE2AyKkYn1I5FaSQyKkonxItcfLLW4RmiUPM/RWW6Y+Z+yva2v6Da0WaZsS1Is1kRE3FAEne4NKGJm+ACkkkIgOVJFKRgUoSqViJFJGKnBkkEpEzowmJFIOxAYnIyd8l3kWXWAnFDVWvogeJDQyNBrabBnbUBk4aDZwXsaf+BiRqq1tBKuS4noLOfor9DSuRYvI3ceGPnBoNSKSYGU18vtjAp8RAiSQzAxmpJGnTxJdScHmjrWwDpZAmbXB5Q5M2uLyhSRtc3tCkDSxviFoR1YxErYi6SyVqRVQzErUiqBmpWhHTjCSXGhHEZKRqRYxTtTW9I+9UqpERkHcqmU8BTiXL04C0U8nyNCDtVKq5vyLsVEKfSjuVLmwConsqZRFly0gYNgHJMmprSSGnkLSIknNDW0kaKYW0RZTrRm0d35AJVcqZGBEpI+lMjEhcxRFup3sENlXirFmptiq5TQO1ViW36Yu6VKVO00hVO9I34kpFO9LduqUo1mggaiKlGi1ETaQscgwVsfRYZSJNI2VOteTTwsGh/dRZFEk0MzFWSiQaGfuRkkg1lTZdYgJTgdoldolGKFBoTGLR6G9Aoq2hUbSG29puig5Tpo6LhUdi7afOofASzlIzFn6WasmpZQotjQ3/l4wV1+FWurHmQw0bGus+mrKgsfbDN36N9V9oIN/j8v9V7wnUuSr0nVTi//oq9z0/1oaU/G0YZUOKtOEGYUOK//aNriERP2CkMivot8REhcS9z48kWaHvgWMo5Ix9sebj8VQWCReoLHLCvnByx6jSk9MtBdwYbi7lNNyr78V4m8ppxr7z9SvPGS1zkadQviMjUKaKOxOMg7zO+9LzMotMQX03p+dlFtOKCNSMlwvULwasBdxRtxgYEPiieGRaERgoOj9P3D34Qb5dsa8DR5B7tjTk0Y0ctwrfGN7G9atJeyaNXDWrXYWPi6ljWuEljcYVXvjeh3mFPzVi/6/CPfzIVaPT4sjXH7XwnXyL+BI5Hmz6wrlNA8lVzkGaRlKp6qaIyTI6KmKqjI6KmCijqyKeh6qxi4xfnM1G7WcS5mSNc+bTs8BxFTaBT6dqP5E4H05159PPTHWWp4F3p7prxY9jo5tj1I63ZnRyFj5yHBsOffq+p2o/DYSDUx2OjMDeqQ5HRmA/Nly24sGpHkfGC+cjIzB79+nOqW59umWqW59u03+uYmDjuYPq9yydTsc7/wC4X1v6cfhzSgAAAABJRU5ErkJggg==', 'base64'));

INSERT INTO ORGANIZATION (name) VALUES ('University of British Columbia');
INSERT INTO ORGANIZATION (name) VALUES ('Run DMC');

INSERT INTO USERS (username, password, email, organization)
VALUES ('Kanye West',
        '$2a$10$7hZeaGVr5pzM.CKkxG38i.kkLib2CLOA9hLpJPxNLRUSB6vbBx20m', /* Decoded Value = '123' */
        'fake@fake.com',
        (SELECT orgId FROM organization o WHERE o.name = 'University of British Columbia'));
INSERT INTO USERS (username, password, email, organization)
VALUES ('Beyonce Knowles',
        '$2a$10$7hZeaGVr5pzM.CKkxG38i.kkLib2CLOA9hLpJPxNLRUSB6vbBx20m', /* Decoded Value = '123' */
        'fake@fake.com',
        (SELECT orgId FROM organization o WHERE o.name = 'University of British Columbia'));
INSERT INTO USERS (username, password, email, organization)
VALUES ('Jay Z',
        '$2a$10$7hZeaGVr5pzM.CKkxG38i.kkLib2CLOA9hLpJPxNLRUSB6vbBx20m', /* Decoded Value = '123' */
        'fake@fake.com',
        (SELECT orgId FROM organization o WHERE o.name = 'University of British Columbia'));
INSERT INTO USERS (username, password, email, organization)
VALUES ('Rihanna',
        '$2a$10$7hZeaGVr5pzM.CKkxG38i.kkLib2CLOA9hLpJPxNLRUSB6vbBx20m', /* Decoded Value = '123' */
        'fake@fake.com',
        (SELECT orgId FROM organization o WHERE o.name = 'University of British Columbia'));

INSERT INTO USERS (username, password, email, organization)
VALUES ('DMC',
        '$2a$10$7hZeaGVr5pzM.CKkxG38i.kkLib2CLOA9hLpJPxNLRUSB6vbBx20m', /* Decoded Value = '123' */
        'fake@fake.com',
        (SELECT orgId FROM organization o WHERE o.name = 'Run DMC'));
INSERT INTO USERS (username, password, email, organization)
VALUES ('DJ Run',
        '$2a$10$7hZeaGVr5pzM.CKkxG38i.kkLib2CLOA9hLpJPxNLRUSB6vbBx20m', /* Decoded Value = '123' */
        'fake@fake.com',
        (SELECT orgId FROM organization o WHERE o.name = 'Run DMC'));
INSERT INTO USERS (username, password, email, organization)
VALUES ('Jam Master Jay',
        '$2a$10$7hZeaGVr5pzM.CKkxG38i.kkLib2CLOA9hLpJPxNLRUSB6vbBx20m', /* Decoded Value = '123' */
        'fake@fake.com',
        (SELECT orgId FROM organization o WHERE o.name = 'Run DMC'));

INSERT INTO team (orgId, logo, name)
VALUES ((SELECT orgId FROM organization o WHERE o.name = 'University of British Columbia'), null, 'UBC TEAM #1');
INSERT INTO team (orgId, logo, name)
VALUES ((SELECT orgId FROM organization o WHERE o.name = 'University of British Columbia'), null, 'UBC TEAM #2');

INSERT INTO team (orgId, logo, name)
VALUES ((SELECT orgId FROM organization o WHERE o.name = 'Run DMC'), null, 'RUN TEAM #1');
INSERT INTO team (orgId, logo, name)
VALUES ((SELECT orgId FROM organization o WHERE o.name = 'Run DMC'), null, 'RUN TEAM #2');

INSERT INTO TEAM_MEMBERS (userid, teamid)
VALUES ((SELECT userid FROM users u WHERE u.username = 'Kanye West'), (SELECT teamid FROM team t WHERE t.name = 'UBC TEAM #1'));
INSERT INTO TEAM_MEMBERS (userid, teamid)
VALUES ((SELECT userid FROM users u WHERE u.username = 'Beyonce Knowles'), (SELECT teamid FROM team t WHERE t.name = 'UBC TEAM #1'));
INSERT INTO TEAM_MEMBERS (userid, teamid)
VALUES ((SELECT userid FROM users u WHERE u.username = 'Jay Z'), (SELECT teamid FROM team t WHERE t.name = 'UBC TEAM #2'));
INSERT INTO TEAM_MEMBERS (userid, teamid)
VALUES ((SELECT userid FROM users u WHERE u.username = 'Rihanna'), (SELECT teamid FROM team t WHERE t.name = 'UBC TEAM #2'));

INSERT INTO TEAM_MEMBERS (userid, teamid)
VALUES ((SELECT userid FROM users u WHERE u.username = 'DMC'), (SELECT teamid FROM team t WHERE t.name = 'RUN TEAM #1'));
INSERT INTO TEAM_MEMBERS (userid, teamid)
VALUES ((SELECT userid FROM users u WHERE u.username = 'DJ Run'), (SELECT teamid FROM team t WHERE t.name = 'RUN TEAM #1'));
INSERT INTO TEAM_MEMBERS (userid, teamid)
VALUES ((SELECT userid FROM users u WHERE u.username = 'Jam Master Jay'), (SELECT teamid FROM team t WHERE t.name = 'RUN TEAM #2'));

INSERT INTO PROJECTS (projectName, createdBy)
VALUES ('UBC Project #1', (SELECT teamid FROM team t WHERE t.name = 'UBC TEAM #1'));
INSERT INTO PROJECTS (projectName, createdBy)
VALUES ('UBC Project #2', (SELECT teamid FROM team t WHERE t.name = 'UBC TEAM #1'));

INSERT INTO PROJECTS (projectName, createdBy)
VALUES ('RUN Project #1', (SELECT teamid FROM team t WHERE t.name = 'RUN TEAM #1'));
INSERT INTO PROJECTS (projectName, createdBy)
VALUES ('RUN Project #2', (SELECT teamid FROM team t WHERE t.name = 'RUN TEAM #1'));

INSERT INTO sprints (sprintNumber, capacity, startDate, endDate, belongsTo, sprintLoad)
VALUES (
1,
50,
(SELECT TO_TIMESTAMP('11 13 20', 'MM-DD-YY')),
(SELECT TO_TIMESTAMP('11 20 20', 'MM-DD-YY')),
(SELECT p.projectId from PROJECTS p WHERE p.projectName = 'UBC Project #1'),
50);

INSERT INTO sprints (sprintNumber, capacity, startDate, endDate, belongsTo, sprintLoad)
VALUES (
2,
50,
(SELECT TO_TIMESTAMP('11 21 20', 'MM-DD-YY')),
(SELECT TO_TIMESTAMP('11 28 20', 'MM-DD-YY')),
(SELECT p.projectId from PROJECTS p WHERE p.projectName = 'UBC Project #1'),
50);

INSERT INTO sprints (sprintNumber, capacity, startDate, endDate, belongsTo, sprintLoad)
VALUES (
3,
50,
(SELECT TO_TIMESTAMP('11 13 20', 'MM-DD-YY')),
(SELECT TO_TIMESTAMP('11 20 20', 'MM-DD-YY')),
(SELECT p.projectId from PROJECTS p WHERE p.projectName = 'UBC Project #2'),
50);

INSERT INTO sprints (sprintNumber, capacity, startDate, endDate, belongsTo, sprintLoad)
VALUES (
4,
50,
(SELECT TO_TIMESTAMP('11 21 20', 'MM-DD-YY')),
(SELECT TO_TIMESTAMP('11 28 20', 'MM-DD-YY')),
(SELECT p.projectId from PROJECTS p WHERE p.projectName = 'UBC Project #2'),
50);

INSERT INTO sprints (sprintNumber, capacity, startDate, endDate, belongsTo, sprintLoad)
VALUES (
1,
50,
(SELECT TO_TIMESTAMP('11 21 20', 'MM-DD-YY')),
(SELECT TO_TIMESTAMP('11 28 20', 'MM-DD-YY')),
(SELECT p.projectId from PROJECTS p WHERE p.projectName = 'RUN Project #1'),
50);

INSERT INTO sprints (sprintNumber, capacity, startDate, endDate, belongsTo, sprintLoad)
VALUES (
2,
50,
(SELECT TO_TIMESTAMP('11 21 20', 'MM-DD-YY')),
(SELECT TO_TIMESTAMP('11 28 20', 'MM-DD-YY')),
(SELECT p.projectId from PROJECTS p WHERE p.projectName = 'RUN Project #1'),
50);

INSERT INTO sprints (sprintNumber, capacity, startDate, endDate, belongsTo, sprintLoad)
VALUES (
1,
50,
(SELECT TO_TIMESTAMP('11 21 20', 'MM-DD-YY')),
(SELECT TO_TIMESTAMP('11 28 20', 'MM-DD-YY')),
(SELECT p.projectId from PROJECTS p WHERE p.projectName = 'RUN Project #2'),
50);

INSERT INTO sprints (sprintNumber, capacity, startDate, endDate, belongsTo, sprintLoad)
VALUES (
2,
50,
(SELECT TO_TIMESTAMP('11 21 20', 'MM-DD-YY')),
(SELECT TO_TIMESTAMP('11 28 20', 'MM-DD-YY')),
(SELECT p.projectId from PROJECTS p WHERE p.projectName = 'RUN Project #2'),
50);

INSERT INTO tickets
VALUES (
DEFAULT,
'Add the ability to add tickets',
'high',
'inProgress',
(SELECT projectId FROM PROJECTS WHERE projectName= 'Project #1'),
1,
(SELECT TO_TIMESTAMP('11 13 20', 'MM-DD-YY')),
(SELECT userid FROM users WHERE username = 'Kanye West'),
(SELECT userid FROM users WHERE username = 'Beyonce Knowles'),
10
);
