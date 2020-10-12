### Database
We are using [docker](https://docs.docker.com/install/) to pull up a local PostgreSQL 10 database.
*Warning*: If you are using Windows, docker is only available for windows pro, which costs ~ $100. 

- `docker-compose up` to start postgres + adminer via Docker.
- `docker-compose stop && docker-compose rm` to delete the containers and their data.

Once the DB is running you can inspect it via the following URL. [http://localhost:5401/?pgsql=db&username=dev&db
=sprintplanner&ns=public](http://localhost:5401/?pgsql=db&username=dev&db=sprintplanner&ns=public)
You might need to login with user `dev` and password `123`.

### FLYWAY
Flyway is one of two open source schema management tools for Java, the other is liquibase. I haven't used flyway
before, but I chose it because I think it looks a little easier to use than liquibase and it's SQL based, which seems
 more in line with the intentions of the course.

Flyway is super easy to use. If you want to make a change to the database just write the SQL to a file in 
src/main/resources/db/migration with a name in the form *V#.#__description.sql*. For development on this project, we
will likely just be modifying *V1.0__init* until we have the schema we want.

You can see how this works by starting the database as described above and then running the SprintPlannerServer.
If you navigate to the adminer URL (above) you should see two tables:

**flyway_schema_history**: This is the table that flyway uses to track schema changes, so it knows if whether to perform
a new migration. This table should have a single entry, because only one migration has been performed.

**tickets**: This is the table created by my mock schema. It should have three fields and a single entry.

We don't need to use flyway, but I think it's a nice way to keep our schema in one place. And I would guess that
something like this is used in most Java database applications.

### JPA
This application is using Hibernate, an implementation of the Java Persistence API (JPA). It maps Java objects to the
relational database tables. A nice feature of JPA is that it can generate SQL, but we aren't allowed to use tools
that generate SQL for us. That's okay because we can use CrudRepositories with @Query annotation to write our own
 SQL.

I added a mock entity based on some examples I found online. The Ticket object maps directly to the Tickets table and
the TicketRepository can be used to query the table and save to the table. Check out the TicketRepository and
TicketServiceImpl classes to see how this works.

Again, we don't need to use jpa. In fact, it's kind of convoluted since we have to write all of our own SQL.

### Server (Spring Boot)
The server runs on port 8080. There are a couple mock endpoints to test things out. Note that you must create a user
 and login to get the jwt or all endpoints will be forbidden.

GET */ticket* will respond with all the tickets in tickets table. (Make sure to spin upo the db before hitting that
 endpoint)
 
GET */ticket/hello* will respond with "Hello World", I was just making sure spring was properly doing its black magic.

POST */users/login* Body '{"username": "username", "password": "password"}' authenticates this user and responds with
 a jwt
 
POST */users/signup* Body '{"username": "username", "password": "password"}' creates a new user and saves to the db

- `mvn clean package` to create an executable jar or just run main.

### Auth0 Authentication
This is probably overkill for the project, but to me an app with users doesn't make sense without user authentication.
 We are using Auth0 to handle user authentication and authorization. When the user signs up they create an entry in the
 USERS table with their username and hashed password. Once they
 have done that users can log in to receive a jwt that is used to authorize subsequent requests and identify the user. 
 
 All endpoints require the following header:
 "Authorization: Bearer xxx.yyy.zzz" where xxx.yyy.zzz is the jwt.
 
 I followed instructions and used some code from https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/

### Frontend

This project uses a react frontend that communicates with the backend via rest.

Run the following commands from ./client:

- `yarn install` to install dependencies
- `yarn start` to start the webpack dev server on port 9000

Alternatively use from root:

- `yarn --cwd ./client install && yarn --cwd ./client start`