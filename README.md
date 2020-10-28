# Polling Session Manager
Rest API to manage polling sessions.

Access the wiki to see installation [Requirements](https://github.com/DouglasGiordano/polling-session-manager/wiki/Requirements).

### Technologies
- Java 11
- Spring Boot
- Mongo DB
- RabbitMQ
- Jackson
- JUnit 5
- Model Mapper

### RUN on Ubuntu
Install Gradle, MongoDB, RabbitMQ and Java 11
Execute MongoDB (Port 27017) and RabbitMQ (Port 5672|15672)

`gradle clean`

`gradle build -x test`

`gradle jar`

`java -jar build/libs/polling-session-manager-0.0.1-SNAPSHOT.jar`


### RUN on Docker
Install Docker and Docker-Compose

`gradle clean`

`gradle build -x test`

`gradle jar`

`docker-compose up -d`

### How to use?
The API consists of 4 paths 1 a queue.

- **Create agenda[POST Request]:** Create a new agenda (Pauta) (localhost:8080/api/v1/agenda).

-  **Open Voting Session [POST Request]:** Opens a polling session for an agenda for a specified period of time. After the time the agenda is closed (localhost:8080/api/v1/agenda/[ID Agenda]/voting/open).

- **Vote [POST Request]:** Records a member's vote (valid CPF) in a session open for voting (localhost:8080/api/v1/agenda/[ID Agenda]/voting/vote).

- **Access Results (API) [GET Request]:** Returns the number of YES and NO votes and the result of the vote (localhost:8080/api/v1/agenda/[ID Agenda]/voting/result).
  
- **Access Results (Queue):** The queue name is "polling-session-result".

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
