package com.autoever.backend.user.controller;

import com.autoever.backend.user.User;
import com.autoever.backend.user.UserService;
import com.autoever.backend.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 사용자 API 컨트롤러
 * - 내 정보 조회(/api/users/me) 등 사용자 관련 엔드포인트 제공
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    /**
     * 내 정보 조회 API
     * @param token 로그인 시 발급받은 토큰 (X-Auth-Token 헤더)
     * @return UserDto (주소는 대분류만 반환)
     */
    @GetMapping("/me")
    public ResponseEntity<UserDto> me(@RequestHeader("X-Auth-Token") String token) {
        User user = userService.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("invalid token"));
        // 주소에서 가장 큰 단위(예: "서울특별시")만 추출
        String region = user.getAddress().split(" ")[0];
        User sanitized = User.builder()
                .id(user.getId())
                .account(user.getAccount())
                .name(user.getName())
                .regNo(user.getRegNo())
                .phone(user.getPhone())
                .address(region)
                .build();
        return ResponseEntity.ok(UserDto.from(sanitized));
    }
}
