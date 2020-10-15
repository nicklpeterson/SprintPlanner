CREATE TYPE SEVERITY AS ENUM ('high', 'medium', 'low');
CREATE TYPE STATUS AS ENUM ('backlog', 'paused', 'inProgress', 'inReview', 'done');

CREATE TABLE DISPLAY_PICTURE (
    id UUID PRIMARY KEY,
    img BYTEA
);

CREATE TABLE USERS (
    id UUID PRIMARY KEY,
    username VARCHAR(255) UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE,
    display_picture_id UUID NOT NULL,
    FOREIGN KEY (display_picture_id) REFERENCES DISPLAY_PICTURE(id)
);

CREATE TABLE TICKETS (
    id UUID PRIMARY KEY,
    ticket_name VARCHAR(255),
    creator VARCHAR(255),
    status_change_date TIMESTAMP,
    ticket_description TEXT,
    severity SEVERITY,
    status STATUS,
    date_issued TIMESTAMP
);

CREATE TABLE TICKET_CREATOR (
    user_id UUID,
    ticket_id UUID,
    PRIMARY KEY (user_id, ticket_id),
    FOREIGN KEY (user_id) REFERENCES USERS(id),
    FOREIGN KEY (ticket_id) REFERENCES TICKETS(id)
);

CREATE TABLE TICKET_ASSIGNEE (
    user_id UUID,
    ticket_id UUID,
    PRIMARY KEY (user_id, ticket_id),
    FOREIGN KEY (user_id) REFERENCES USERS(id),
    FOREIGN KEY (ticket_id) REFERENCES TICKETS(id)
);

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
/* There has to be a better way to add the default image.*/
/* The db needs access to the image from the server but I can't find a way to that with SQL So I converted it to base64. Gnaaahhh!*/
INSERT INTO DISPLAY_PICTURE VALUES (
    uuid_nil(),
    decode('iVBORw0KGgoAAAANSUhEUgAAAOIAAADfCAMAAADcKv+WAAAAFVBMVEX29vbm5ubs7Ozo6Oj09PTr6+vw8PAPpVovAAAEoklEQVR4nO2d0ZajMAxDp0D5/09eUjYHaElLEgvLTu7DPKNjWXbSlvn763Q6nU6n0+l0zpnmhSH8mbQfBcA8jONjzzgOs/ZDyTHNR3U7nT7KOT8T+qJK7QesJVnAHaZFXhFoWuR0UaBduw6XBQae2o+bT0YJjbp1zhW4MGg/dBbfB0WKUfuxM8g2adRoZhMoVbhgQ+NULtCKxooaGtFYqdCAxmqF9Lmat9Kcw73olEz8T5h3gLow3SDe5eob8T/aQpJINOIKaztK2TRAalUxmz5YJ4dMmkYoyyiqkDJx5LJmhXA4Cisk3FWli0hYRnGFdN0oG6crZKEqORMjXLNRcrHZoCpj2aXiL6g2VYhCqsBBhE2AyKkYn1I5FaSQyKkonxItcfLLW4RmiUPM/RWW6Y+Z+yva2v6Da0WaZsS1Is1kRE3FAEne4NKGJm+ACkkkIgOVJFKRgUoSqViJFJGKnBkkEpEzowmJFIOxAYnIyd8l3kWXWAnFDVWvogeJDQyNBrabBnbUBk4aDZwXsaf+BiRqq1tBKuS4noLOfor9DSuRYvI3ceGPnBoNSKSYGU18vtjAp8RAiSQzAxmpJGnTxJdScHmjrWwDpZAmbXB5Q5M2uLyhSRtc3tCkDSxviFoR1YxErYi6SyVqRVQzErUiqBmpWhHTjCSXGhHEZKRqRYxTtTW9I+9UqpERkHcqmU8BTiXL04C0U8nyNCDtVKq5vyLsVEKfSjuVLmwConsqZRFly0gYNgHJMmprSSGnkLSIknNDW0kaKYW0RZTrRm0d35AJVcqZGBEpI+lMjEhcxRFup3sENlXirFmptiq5TQO1ViW36Yu6VKVO00hVO9I34kpFO9LduqUo1mggaiKlGi1ETaQscgwVsfRYZSJNI2VOteTTwsGh/dRZFEk0MzFWSiQaGfuRkkg1lTZdYgJTgdoldolGKFBoTGLR6G9Aoq2hUbSG29puig5Tpo6LhUdi7afOofASzlIzFn6WasmpZQotjQ3/l4wV1+FWurHmQw0bGus+mrKgsfbDN36N9V9oIN/j8v9V7wnUuSr0nVTi//oq9z0/1oaU/G0YZUOKtOEGYUOK//aNriERP2CkMivot8REhcS9z48kWaHvgWMo5Ix9sebj8VQWCReoLHLCvnByx6jSk9MtBdwYbi7lNNyr78V4m8ppxr7z9SvPGS1zkadQviMjUKaKOxOMg7zO+9LzMotMQX03p+dlFtOKCNSMlwvULwasBdxRtxgYEPiieGRaERgoOj9P3D34Qb5dsa8DR5B7tjTk0Y0ctwrfGN7G9atJeyaNXDWrXYWPi6ljWuEljcYVXvjeh3mFPzVi/6/CPfzIVaPT4sjXH7XwnXyL+BI5Hmz6wrlNA8lVzkGaRlKp6qaIyTI6KmKqjI6KmCijqyKeh6qxi4xfnM1G7WcS5mSNc+bTs8BxFTaBT6dqP5E4H05159PPTHWWp4F3p7prxY9jo5tj1I63ZnRyFj5yHBsOffq+p2o/DYSDUx2OjMDeqQ5HRmA/Nly24sGpHkfGC+cjIzB79+nOqW59umWqW59u03+uYmDjuYPq9yydTsc7/wC4X1v6cfhzSgAAAABJRU5ErkJggg==',
    'base64')
);

/* Do not populate the user table using SQL. The server encodes and decodes passwords, so there is no way for a user added
 here to login. Just start the frontend and add a user at http://localhost:9000/register*/