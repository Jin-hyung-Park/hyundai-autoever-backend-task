package com.autoever.backend.user.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 로그인 요청 정보를 담는 DTO 클래스입니다.
 * - 클라이언트가 로그인 시 전달하는 데이터 구조
 */
@Getter
@Setter
public class LoginRequest {
    private String account;  // 계정
    private String password; // 비밀번호(평문)
}
