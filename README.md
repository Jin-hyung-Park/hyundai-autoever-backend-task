# 현대오토에버 백엔드 과제

이 프로젝트는 Spring Boot 기반의 회원 관리 REST API 서버입니다. 회원가입, 로그인, 내 정보 조회, 관리자 회원 관리(조회/수정/삭제) 기능을 제공합니다.

## 요구사항
- Java 17 이상
- Gradle 8.x
- H2 Database (내장)
- (선택) curl 또는 Postman 등 API 테스트 도구

## 실행 방법

1. **프로젝트 클론**
   ```sh
   git clone https://github.com/Jin-hyung-Park/hyundai-autoever-backend-task.git 
   cd hyundai-autoever-backend-task-master
   ```

2. **빌드 및 실행**
   ```sh
   ./gradlew clean build
   ./gradlew bootRun
   ```
   또는
   ```sh
   ./gradlew build
   java -jar build/libs/*.jar
   ```

3. **H2 콘솔 접속**
   - [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
   - JDBC URL: `jdbc:h2:mem:testdb`
   - username: `sa`, password: (빈값)

## API 테스트 예시 (curl)

### 1. 회원가입
```sh
curl -X POST http://localhost:8080/api/users/signup \
  -H "Content-Type: application/json" \
  -d '{
    "account": "testuser",
    "password": "1234",
    "name": "홍길동",
    "regNo": "900101-1234567",
    "phone": "010-1234-5678",
    "address": "서울시 강남구"
  }'
```

### 2. 로그인 (토큰 발급)
```sh
curl -X POST http://localhost:8080/api/users/login \
  -H "Content-Type: application/json" \
  -d '{
    "account": "testuser",
    "password": "1234"
  }'
```

### 3. 내 정보 조회
```sh
curl -X GET http://localhost:8080/api/users/me \
  -H "X-Auth-Token: <로그인_토큰>"
```

### 4. 관리자 API (Basic 인증: admin/1212) 테스트

#### 가. 회원 목록 조회
```sh
curl -X GET "http://localhost:8080/api/admin/users?page=0&size=10" \
  -u admin:1212
```

#### 나. 회원 정보 수정
```sh
curl -X PUT http://localhost:8080/api/admin/users/1 \
  -u admin:1212 \
  -H "Content-Type: application/json" \
  -d '{
    "password": "새로운비밀번호",
    "address": "서울특별시"
  }'
```

#### 다. 회원 삭제
```sh
curl -X DELETE http://localhost:8080/api/admin/users/1 \
  -u admin:1212
```

### 5. 연령대별 테스트 데이터 생성(회원가입)
아래 예시를 참고하여 20대, 30대, 40대, 50대 회원을 각각 1명씩 생성할 수 있습니다.

##### 가. 20대 회원 생성
```sh
curl -X POST http://localhost:8080/api/users/signup \
  -H "Content-Type: application/json" \
  -d '{
    "account": "user20",
    "password": "1234",
    "name": "성춘향",
    "regNo": "040101-1234567",
    "phone": "010-2020-2020",
    "address": "서울시 20구"
  }'
```
##### 나. 30대 회원 생성
```sh
curl -X POST http://localhost:8080/api/users/signup \
  -H "Content-Type: application/json" \
  -d '{
    "account": "user30",
    "password": "1234",
    "name": "이몽령",
    "regNo": "950101-1234567",
    "phone": "010-3030-3030",
    "address": "서울시 30구"
  }'
```
##### 다. 40대 회원 생성
```sh
curl -X POST http://localhost:8080/api/users/signup \
  -H "Content-Type: application/json" \
  -d '{
    "account": "user40",
    "password": "1234",
    "name": "호날두",
    "regNo": "850101-1234567",
    "phone": "010-4040-4040",
    "address": "서울시 40구"
  }'
```
##### 라. 50대 회원 생성
```sh
curl -X POST http://localhost:8080/api/users/signup \
  -H "Content-Type: application/json" \
  -d '{
    "account": "user50",
    "password": "1234",
    "name": "조단",
    "regNo": "750101-1234567",
    "phone": "010-5050-5050",
    "address": "서울시 50구"
  }'
```

#### 마. 연령대별 메시지 발송 (관리자)
```sh
curl -X POST "http://localhost:8080/api/admin/users/send-message" \
  -H "Content-Type: application/json" \
  -u admin:1212 \
  -d '{"ageGroup":"30대","message":"이벤트 안내 메시지입니다."}'
```
- `ageGroup` : "20대", "30대", "40대", "50대" 등 지정 가능
- `message` : 원하는 메시지 본문 입력
- 관리자 인증 필요: `-u admin:1212`

- 연령대별 메시지 발송 기능은 카카오톡/문자 API(모의)와 연동되며, 분당 호출 제한 및 실패 시 대체 발송 로직이 적용되어 있습니다.

## 참고 사항
- 서버를 재시작하면 H2 메모리 DB의 데이터가 모두 초기화됩니다. (테스트 시 회원가입부터 진행 필요)
- 관리자 API는 Basic 인증(admin/1212) 필요

---

문의사항은 이슈로 남겨주세요.
