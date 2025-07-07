# PROMPT.md

이 파일은 과제 수행 중 GitHub Copilot(코파일럿) 및 AI 어시스턴트에게 실제로 요청한 주요 프롬프트(질문/명령)와 그에 대한 활용 내역을 기록한 문서입니다.

---

## 사용 프롬프트 예시

### 1. H2 콘솔 및 Spring Security 설정 오류 해결
```
서버 구동시 h2 consol 에 접근할 수 없다고 나는데, 조치하는 방법 알려줘
Spring Security 환경에서 h2-console 접근 허용하는 방법 알려줘
```
- application.properties에 H2 콘솔 설정 추가 및 SecurityConfig에서 접근 허용 방법 안내받음.
- SecurityConfig.java에 CSRF/FrameOptions 비활성화 및 h2-console 경로 permitAll() 적용.

### 2. Copilot 활용 내역(일부)
- 회원가입/로그인/관리자 API의 기본 컨트롤러 및 서비스 뼈대 코드를 Copilot 자동완성으로 생성 후, 직접 상세 로직을 보완함
- User, UserDto, SignupRequest 등 주요 DTO/엔티티 클래스의 필드 선언 및 getter/setter 자동 생성에 Copilot 활용
- Spring Security 설정(SecurityConfig) 기본 구조 자동완성에 Copilot 활용
- 반복적인 주석 추가, DTO 변환 메서드 등은 Copilot 제안 코드 기반으로 일부 적용
- API 명세에 맞는 DTO/엔티티 설계, 예외처리, 한글 주석 자동화 등에서 Copilot의 코드 제안을 참고하여 일부 반영함

### 3. REST API 테스트용 curl 명령어 요청
```
Rest api 호출하여 테스트하고 싶습니다.
curl 명령어 제공부탁해요
```
- 회원가입, 로그인, 내 정보 조회, 관리자 API 등 주요 curl 예시 제공받음.

### 4. README.md 작성 요청
```
다른사람이 제 깃허브 레파지토리를 받아서 로컬에서 수행해볼수 있도록 README.md를 작성해주세요.
```
- 실행법, API 예시, H2 콘솔, 참고사항 등 한글로 상세한 README.md 생성.

---

※ 위 프롬프트 및 Copilot 활용 내역은 실제로 AI에게 요청한 주요 내용만을 요약해 작성하였습니다.
