package com.autoever.backend.user.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 회원 정보 수정 요청 정보를 담는 DTO 클래스입니다.
 * - 관리자 API에서 회원의 비밀번호/주소를 수정할 때 사용
 */
@Getter
@Setter
public class UpdateRequest {
    private String password; // 새 비밀번호(선택)
    private String address;  // 새 주소(선택)
}
