package com.autoever.backend.user;

import jakarta.persistence.*;
import lombok.*;

/**
 * 회원 정보를 저장하는 JPA 엔티티 클래스입니다.
 * - DB 테이블(users)과 매핑됩니다.
 * - 계정(account), 주민등록번호(regNo)는 유일해야 합니다.
 */
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "account"), // 계정 유니크 제약
        @UniqueConstraint(columnNames = "regNo")    // 주민등록번호 유니크 제약
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    /**
     * 회원 고유 ID (PK, 자동 증가)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 계정 (로그인 ID, 유일)
     */
    @Column(nullable = false, unique = true)
    private String account;

    /**
     * 암호화된 비밀번호
     */
    @Column(nullable = false)
    private String password;

    /**
     * 이름
     */
    @Column(nullable = false)
    private String name;

    /**
     * 주민등록번호 (유일)
     */
    @Column(nullable = false, unique = true)
    private String regNo;

    /**
     * 핸드폰 번호
     */
    @Column(nullable = false)
    private String phone;

    /**
     * 주소 (전체 주소)
     */
    @Column(nullable = false)
    private String address;
}
