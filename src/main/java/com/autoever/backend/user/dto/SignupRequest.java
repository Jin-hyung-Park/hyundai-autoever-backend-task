package com.autoever.backend.user.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 회원가입 요청 정보를 담는 DTO 클래스입니다.
 * - 클라이언트가 회원가입 시 전달하는 데이터 구조
 */
@Getter
@Setter
public class SignupRequest {
    private String account;  // 계정
    private String password; // 비밀번호(평문)
    private String name;     // 이름
    private String regNo;    // 주민등록번호
    private String phone;    // 핸드폰 번호
    private String address;  // 주소
}
