package com.autoever.backend.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 회원 엔티티(User)에 대한 DB 접근을 담당하는 JPA 리포지토리 인터페이스입니다.
 * - Spring Data JPA가 구현체를 자동 생성합니다.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 계정(account) 중복 여부 확인
     */
    boolean existsByAccount(String account);
    /**
     * 주민등록번호(regNo) 중복 여부 확인
     */
    boolean existsByRegNo(String regNo);
    /**
     * 계정으로 회원 조회
     */
    Optional<User> findByAccount(String account);
}
