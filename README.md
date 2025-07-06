# hyundai-autoever-backend-task

This project is a simple Spring Boot application used for the Hyundai AutoEver backend task. It exposes a single REST endpoint to verify the server is running.

## Requirements

- Java 17 or later
- Maven 3.8+

## Running the application

Clone the repository and build the project using Maven:

```bash
mvn clean package
```

Run the generated JAR file:

```bash
java -jar target/security-service-0.0.1-SNAPSHOT.jar
```

The application will start on port `8080`. You can verify it by opening your browser or executing:

```bash
curl http://localhost:8080/hello
```

You should see the response `Hello, world`.

## Docker and other services

This project does not require external services like Redis or a database. If you wish to run Redis for future development you can use:

```bash
docker pull redis:latest && docker run -d -p 6379:6379 redis:latest
```

## Prompt history
All prompts used with AI assistance for this task are recorded in [PROMPT.md](PROMPT.md).
