package com.autoever.backend.user.controller;

import com.autoever.backend.user.User;
import com.autoever.backend.user.UserService;
import com.autoever.backend.user.dto.LoginRequest;
import com.autoever.backend.user.dto.SignupRequest;
import com.autoever.backend.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class AuthController {
    private final UserService userService;

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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String token = userService.login(request.getAccount(), request.getPassword());
        return ResponseEntity.ok(token);
    }
}
