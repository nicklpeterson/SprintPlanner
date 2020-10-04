### Database
We are using [docker](https://docs.docker.com/install/) to pull up a local PostgreSQL 10 database.
*Warning*: If you are using Windows, docker is only available for windows pro, which costs ~ $100. 

- `docker-compose up` to start postgres + adminer via Docker.
- `docker-compose stop && docker-compose rm` to delete the containers and their data.

Once the DB is running you can inspect it via the following URL. [http://localhost:5401/?pgsql=db&username=dev&db
=sprintplanner&ns=public](http://localhost:5401/?pgsql=db&username=dev&db=sp&ns=public)
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

### Hibernate
Hibernate is an implementation of Java Persistence API (JPA). It maps Java objects to the relational database tables.
This allows us to work with Java objects and write less SQL.  

I added a mock entity based on some examples I found online. The Ticket object maps directly to the Tickets table and
the TicketRepository can be used to query the table and save to the table without writing any SQL (you can write custom
sql queries if you need to). Check out the TicketServiceImpl class to see an example of how this works.

Again, we don't need to use hibernate. The course profs said there won't be TA support, if we go rogue and use
tools like hibernate. That said, there is a ton of information online and hibernate is another tool that we may actually
see outside of academia.

### Server (Spring Boot)
The server runs on port 8080. There are a couple mock endpoints to test things out:

*/rest/tickets* will respond with all the tickets in tickets table. (Make sure to spin upo the db before hitting that
 endpoint)
 
*/rest/hello* will respond with "Hello World", I was just making sure spring was properly doing its black magic.

### Ngrok
At some point we may want to test our server on a publicly available URL.
This is easy with ngrok [which you might need to install first](https://ngrok.com/).

- Run `ngrok http 8080` to stark ngrok and get a public URL.
- Set the env var `BASE_URL` to your ngrok URL in your IntelliJ run configuration.

You can monitor all requests coming through ngrok at `http://localhost:4040`.

### Frontend

No development on this yet. As a group we have talked about doing the frontend in react. Need more discussion on how
 we want to do that.
