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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserDto> me(@RequestHeader("X-Auth-Token") String token) {
        User user = userService.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("invalid token"));
        // return only big region in address
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
