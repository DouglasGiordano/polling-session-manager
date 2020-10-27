# Polling Session Manager
Rest API to manage polling sessions.

Access the wiki to see installation requirements.

https://github.com/DouglasGiordano/polling-session-manager/wiki/Requirements

### RUN on Ubuntu
Install Gradle, MongoDB, RabbitMQ and Java 11

`gradle clean`

`gradle build -x test`

`gradle jar`

`java -jar /build/libs/`


### RUN on Docker
Install Docker and Docker-Compose

`gradle clean`

`gradle build -x test`

`gradle jar`

`docker-compose up -d`

### Use
The API consists of 4 paths 1 a queue.

- Create agenda: [POST Request] localhost:8080/api/v1/agenda

- Open Voting Session: [POST Request] localhost:8080/api/v1/agenda/[ID Agenda]/voting/open
  
> Example: http://localhost:8080/api/v1/agenda/5f97b2b0f9e9b543df8de7bc/voting/open

- Vote: [POST Request] localhost:8080/api/v1/agenda/<ID Agenda>/voting/vote
  
> Example: http://localhost:8080/api/v1/agenda/5f97b2b0f9e9b543df8de7bc/voting/vote

- Access Results (API): [GET Request] localhost:8080/api/v1/agenda/[ID Agenda]/voting/result
  
> Example: http://localhost:8080/api/v1/agenda/5f97b2b0f9e9b543df8de7bc/voting/result
  
- Access Results (Queue): The queue name is "polling-session-result"

### Documentation
The swagger was configured in the project. To access use the URL below.

http://localhost:8080/swagger-ui.html

### Project Decisions 
- Used Async contained in the spring framework to execute asynchronous methods. It was used to count the time and close a voting session.
- Used Lombok to improve the readability of the code.
- RabbitMQ used for a queued messaging structure. RabbitMQ was chosen because it is simple and has direct integration with the Spring framework. RabbitMQ was used to deliver the voting result to the queue called "polling-session-result".
- Used @Service to separate logic from entities. Improves code readability.
- Used the log structure simplified by Lombok. It is simple and easy to offer maintenance.
- Created a class called ApiError to return any system status that is not part of the happy path.
- We use the Transfer Object (TO) standard to facilitate the transfer of information and not change the semantics of the entities.
