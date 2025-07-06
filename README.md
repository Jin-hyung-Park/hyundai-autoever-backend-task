# Backend Task

This project is a simple Spring Boot application that provides user registration and management APIs.

## Requirements
- Java 17+
- Gradle 8+

## Running the Application
The application uses an in-memory H2 database so no additional setup is required.

```bash
# run tests (optional)
gradle test

# run the server
gradle bootRun
```

Access the H2 console at `http://localhost:8080/h2-console` with JDBC URL `jdbc:h2:mem:testdb`.

## Admin Credentials
Basic authentication is required for admin APIs.
- **username:** `admin`
- **password:** `1212`

## Example Usage
```bash
# signup
curl -X POST http://localhost:8080/api/users/signup \
  -H 'Content-Type: application/json' \
  -d '{"account":"user1","password":"pass","name":"User","regNo":"1234567890123","phone":"01012345678","address":"서울특별시 강남구"}'

# login
TOKEN=$(curl -s -X POST http://localhost:8080/api/users/login -H 'Content-Type: application/json' -d '{"account":"user1","password":"pass"}')

# my page
curl http://localhost:8080/api/users/me -H "X-Auth-Token: $TOKEN"
```

Admin example:
```bash
# list users
curl -u admin:1212 http://localhost:8080/api/admin/users
```
