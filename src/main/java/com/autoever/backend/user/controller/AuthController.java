package com.autoever.backend.user.controller;

import com.autoever.backend.user.User;
import com.autoever.backend.user.UserService;
import com.autoever.backend.user.dto.LoginRequest;
import com.autoever.backend.user.dto.SignupRequest;
import com.autoever.backend.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 인증/회원가입 관련 API 컨트롤러
 * - 회원가입, 로그인 엔드포인트 제공
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class AuthController {
    private final UserService userService;

    /**
     * 회원가입 API
     * @param request 회원가입 요청 DTO
     * @return 가입된 회원 정보(UserDto)
     */
    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignupRequest request) {
        User user = User.builder()
                .account(request.getAccount())
                .password(request.getPassword())
                .name(request.getName())
                .regNo(request.getRegNo())
                .phone(request.getPhone())
                .address(request.getAddress())
                .build();
        return ResponseEntity.ok(UserDto.from(userService.signup(user)));
    }

    /**
     * 로그인 API
     * @param request 로그인 요청 DTO
     * @return 인증 토큰(String)
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String token = userService.login(request.getAccount(), request.getPassword());
        return ResponseEntity.ok(token);
    }
}
