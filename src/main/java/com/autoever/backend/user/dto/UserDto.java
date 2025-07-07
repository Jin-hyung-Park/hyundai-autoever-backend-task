package com.autoever.backend.user.dto;

import com.autoever.backend.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 회원 정보를 API 응답용으로 변환하는 DTO 클래스입니다.
 * - 엔티티(User)에서 필요한 정보만 추출해 전달합니다.
 */
@Getter
@AllArgsConstructor
public class UserDto {
    private Long id;         // 회원 고유 ID
    private String account;  // 계정
    private String name;     // 이름
    private String regNo;    // 주민등록번호
    private String phone;    // 핸드폰 번호
    private String address;  // 주소(또는 대분류 주소)

    /**
     * User 엔티티를 UserDto로 변환
     */
    public static UserDto from(User user) {
        return new UserDto(
                user.getId(),
                user.getAccount(),
                user.getName(),
                user.getRegNo(),
                user.getPhone(),
                user.getAddress()
        );
    }
}
