### Database
The schema and initial inserts are located at src/main/resources/db/migration/V1.0_init.sql

Note that we are using Postgres and some of the SQL in that file is specific to Postgres.

This is a FlyWay schema. It's an open source schema management tool for Java. We are using this because it integrates
very well with Spring, making it really easy to run the start up code when we start the program.

We are using [docker](https://docs.docker.com/install/) to pull up a local PostgreSQL 10 database.

- `docker-compose up` to start postgres + adminer via Docker.
- `docker-compose stop && docker-compose rm` to delete the containers and their data.

Once the DB is running you can inspect it via the following URL. [http://localhost:5401/?pgsql=db&username=dev&db
=sprintplanner&ns=public](http://localhost:5401/?pgsql=db&username=dev&db=sprintplanner&ns=public)
You might need to login with user `dev` and password `123`.

This is really nice for development because we can see the tables and data added by our SQL and it's super easy to just
nuke the container and create a new db. And adminer has an SQL portal for testing SQL queries.

### Frontend

This project uses a react frontend that communicates with the backend via rest.

Run the following commands from ./client:

- `yarn install` to install dependencies
- `yarn start` to start the webpack dev server on port 9000

Alternatively use from root:

- `yarn --cwd ./client install && yarn --cwd ./client start`

### JPA
This application is using the Java Persistence API (JPA) to connect with the database. We are using CrudRepositories
 with @Query annotation to write our own SQL, so none of the SQL is "Generated".

### Server (Spring Boot)
The server runs on port 8080. Note that you must create a user
 and login to get the jwt or all endpoints will be forbidden.

POST */users/login* Body '{"username": "username", "password": "password"}' authenticates this user and responds with
 a jwt
 
POST */users/signup* Body '{"username": "username", "password": "password"}' creates a new user and saves to the db

- `mvn clean package` to create an executable jar or just run main.

### Auth0 Authentication
An app with users doesn't make sense without user authentication.
 We are using Auth0 to handle user authentication and authorization. When the user signs up they create an entry in the
 USERS table with their username and hashed password. Once they
 have done that users can log in to receive a jwt that is used to authorize subsequent requests and identify the user. 
 
 All endpoints require the following header:
 "Authorization: Bearer xxx.yyy.zzz" where xxx.yyy.zzz is the jwt.
 
 Add a new user:
 curl -H "Content-Type: application/json" -X POST -d '{
 "username": "test",
 "password": "123","email": 
 "nick@test.com","organizationName": "ubc"
 }' http://localhost:8080/users/signup
 
 Get a User JWT:
 curl -i -H "Content-Type: application/json" -X POST -d '{"username": "nick", "password": "123"}' http://localhost:8080/login
 
 I used instructions and code from [https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot](https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot)
