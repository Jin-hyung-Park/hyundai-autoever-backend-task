package com.autoever.backend.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 회원 관련 비즈니스 로직을 담당하는 서비스 클래스입니다.
 * - 회원가입, 로그인, 회원정보 수정/삭제, 토큰 관리 등 핵심 기능을 제공합니다.
 */
@Service
@RequiredArgsConstructor
public class UserService {
    // 회원 데이터베이스 접근 객체 (JPA Repository)
    private final UserRepository userRepository;
    // 비밀번호 암호화 객체 (BCrypt 등)
    private final PasswordEncoder passwordEncoder;

    // 토큰과 사용자 ID를 매핑하는 인메모리 저장소 (로그인 세션 대체)
    // key: 발급된 토큰(UUID), value: 사용자 id
    private final Map<String, Long> tokenStore = new ConcurrentHashMap<>();
    // RestTemplate를 외부에서 주입받을 수 있도록 필드로 선언
    private final RestTemplate restTemplate;

    /**
     * 회원가입 처리
     * 1. 계정(account)과 주민등록번호(regNo)가 중복되는지 검사
     * 2. 비밀번호를 암호화하여 저장
     * 3. DB에 사용자 정보 저장
     * @param user 가입할 사용자 정보
     * @return 저장된 사용자 엔티티
     * @throws IllegalArgumentException 중복 계정/주민번호가 있을 경우
     */
    public User signup(User user) {
        // 계정 중복 체크
        if (userRepository.existsByAccount(user.getAccount())) {
            throw new IllegalArgumentException("account exists");
        }
        // 주민등록번호 중복 체크
        if (userRepository.existsByRegNo(user.getRegNo())) {
            throw new IllegalArgumentException("regNo exists");
        }
        // 비밀번호 암호화
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // DB에 저장
        return userRepository.save(user);
    }

    /**
     * 로그인 처리 및 토큰 발급
     * 1. 계정으로 사용자 조회
     * 2. 비밀번호 일치 여부 확인 (암호화된 비밀번호 비교)
     * 3. 성공 시 UUID 기반 토큰 발급 및 저장
     * @param account 로그인할 계정
     * @param password 평문 비밀번호
     * @return 인증 토큰(UUID 문자열)
     * @throws IllegalArgumentException 계정이 없거나 비밀번호가 틀릴 경우
     */
    public String login(String account, String password) {
        // 계정으로 사용자 조회 (없으면 예외)
        User user = userRepository.findByAccount(account)
                .orElseThrow(() -> new IllegalArgumentException("invalid credentials"));
        // 비밀번호 일치 여부 확인 (암호화된 비밀번호와 비교)
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("invalid credentials");
        }
        // 토큰(UUID) 발급 및 저장
        String token = UUID.randomUUID().toString();
        tokenStore.put(token, user.getId());
        return token;
    }

    /**
     * 토큰으로 사용자 조회
     * @param token 로그인 시 발급받은 토큰
     * @return Optional<User> (토큰이 유효하지 않으면 empty)
     */
    public Optional<User> findByToken(String token) {
        Long id = tokenStore.get(token); // 토큰으로 사용자 id 조회
        if (id == null) return Optional.empty();
        return userRepository.findById(id); // id로 사용자 정보 조회
    }

    /**
     * 회원 정보 수정 (비밀번호, 주소)
     * @param id 수정할 회원 id
     * @param password 새 비밀번호(선택)
     * @param address 새 주소(선택)
     * @return 수정된 사용자 엔티티
     * @throws NoSuchElementException 해당 id의 사용자가 없을 경우
     */
    public User updateUser(Long id, String password, String address) {
        // id로 사용자 조회 (없으면 예외)
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("user not found"));
        // 비밀번호가 null이 아니면 암호화 후 변경
        if (password != null) {
            user.setPassword(passwordEncoder.encode(password));
        }
        // 주소가 null이 아니면 변경
        if (address != null) {
            user.setAddress(address);
        }
        // DB에 저장
        return userRepository.save(user);
    }

    /**
     * 회원 삭제
     * @param id 삭제할 회원 id
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * 회원 목록 조회 (페이징)
     * @param page 페이지 번호(0부터 시작)
     * @param size 한 페이지당 회원 수
     * @return 회원 목록 리스트
     */
    public List<User> findAll(int page, int size) {
        // PageRequest.of(page, size)로 페이징 처리
        return userRepository.findAll(org.springframework.data.domain.PageRequest.of(page, size)).getContent();
    }

    /**
     * 연령대별 카카오톡/문자 메시지 발송
     * @param ageGroup 연령대(예: "20대", "30대")
     * @param message  메시지 본문(첫줄 자동 생성)
     * @return 발송 시도 인원 수
     */
    public int sendMessageToAgeGroup(String ageGroup, String message) {
        List<User> users = userRepository.findAll();
        int nowYear = LocalDate.now().getYear();
        int minAge = 0, maxAge = 200;
        if (ageGroup != null && ageGroup.endsWith("대")) {
            try {
                int base = Integer.parseInt(ageGroup.replace("대", ""));
                minAge = base;
                maxAge = base + 9;
            } catch (Exception ignored) {}
        }
        int successCount = 0;
        int kakaoCount = 0;
        int smsCount = 0;
        for (User user : users) {
            // 주민등록번호 앞 2자리로 출생년도 추정 (예: 900101-1234567 → 1990년생)
            String regNo = user.getRegNo();
            int birthYear = 1900;
            if (regNo != null && regNo.length() >= 2) {
                int yy = Integer.parseInt(regNo.substring(0, 2));
                int gender = regNo.length() > 7 ? Character.getNumericValue(regNo.charAt(7)) : 1;
                if (gender == 1 || gender == 2) birthYear = 1900 + yy;
                else if (gender == 3 || gender == 4) birthYear = 2000 + yy;
            }
            int age = nowYear - birthYear + 1;
            int ageGroupNum = (age / 10) * 10;
            if (ageGroupNum < minAge || ageGroupNum > maxAge) continue;
            // 메시지 첫줄 생성
            String fullMsg = user.getName() + "님, 안녕하세요. 현대 오토에버입니다.\n" + message;
            boolean kakaoSent = false;
            // 카카오톡 메시지 발송 (분당 100회 제한)
            if (kakaoCount < 100) {
                try {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setBasicAuth("autoever", "1234");
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    String body = String.format("{\"phone\":\"%s\",\"message\":\"%s\"}", user.getPhone(), fullMsg.replace("\n", " "));
                    HttpEntity<String> entity = new HttpEntity<>(body, headers);
                    ResponseEntity<String> resp = restTemplate.postForEntity("http://localhost:8081/kakaotalk-messages", entity, String.class);
                    if (resp.getStatusCode().is2xxSuccessful()) {
                        kakaoSent = true;
                        kakaoCount++;
                    }
                } catch (Exception ignored) {}
            }
            // 실패 시 SMS 발송 (분당 500회 제한)
            if (!kakaoSent && smsCount < 500) {
                try {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setBasicAuth("autoever", "5678");
                    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    String body = "message=" + fullMsg.replace("\n", " ");
                    HttpEntity<String> entity = new HttpEntity<>(body, headers);
                    ResponseEntity<String> resp = restTemplate.postForEntity("http://localhost:8082/sms?phone=" + user.getPhone(), entity, String.class);
                    if (resp.getStatusCode().is2xxSuccessful()) {
                        smsCount++;
                    }
                } catch (Exception ignored) {}
            }
            successCount++;
        }
        return successCount;
    }
}