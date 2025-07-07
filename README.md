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
   git clone <본인 깃허브 레포지토리 주소>
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

### 회원가입
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

### 로그인 (토큰 발급)
```sh
curl -X POST http://localhost:8080/api/users/login \
  -H "Content-Type: application/json" \
  -d '{
    "account": "testuser",
    "password": "1234"
  }'
```

### 내 정보 조회
```sh
curl -X GET http://localhost:8080/api/users/me \
  -H "X-Auth-Token: <로그인_토큰>"
```

### 관리자 API (Basic 인증: admin/1212)

#### 회원 목록 조회
```sh
curl -X GET "http://localhost:8080/api/admin/users?page=0&size=10" \
  -u admin:1212
```

#### 회원 정보 수정
```sh
curl -X PUT http://localhost:8080/api/admin/users/1 \
  -u admin:1212 \
  -H "Content-Type: application/json" \
  -d '{
    "password": "새로운비밀번호",
    "address": "서울특별시"
  }'
```

#### 회원 삭제
```sh
curl -X DELETE http://localhost:8080/api/admin/users/1 \
  -u admin:1212
```

## 참고 사항
- 서버를 재시작하면 H2 메모리 DB의 데이터가 모두 초기화됩니다. (테스트 시 회원가입부터 진행 필요)
- 관리자 API는 Basic 인증(admin/1212) 필요
- 모든 API 상세 설명 및 예시는 `requirements.txt`에도 포함되어 있습니다.

---

문의사항은 이슈로 남겨주세요.
