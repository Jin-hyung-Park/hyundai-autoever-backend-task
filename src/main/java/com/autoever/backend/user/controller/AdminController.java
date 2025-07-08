package com.autoever.backend.user.controller;

import com.autoever.backend.user.User;
import com.autoever.backend.user.UserService;
import com.autoever.backend.user.dto.MessageRequest;
import com.autoever.backend.user.dto.UpdateRequest;
import com.autoever.backend.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 관리자 API 컨트롤러
 * - 회원 목록 조회, 회원 정보 수정/삭제 엔드포인트 제공
 * - Basic 인증 필요
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/users")
public class AdminController {
    private final UserService userService;

    /**
     * 회원 목록 조회 (페이징)
     * @param page 페이지 번호(0부터)
     * @param size 한 페이지당 회원 수
     * @return 회원 정보 리스트(UserDto)
     */
    @GetMapping
    public ResponseEntity<List<UserDto>> list(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        List<UserDto> users = userService.findAll(page, size).stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    /**
     * 회원 정보 수정 (비밀번호, 주소)
     * @param id 회원 id
     * @param request 수정 요청 DTO
     * @return 수정된 회원 정보(UserDto)
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UpdateRequest request) {
        User user = userService.updateUser(id, request.getPassword(), request.getAddress());
        return ResponseEntity.ok(UserDto.from(user));
    }

    /**
     * 회원 삭제
     * @param id 회원 id
     * @return 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 연령대별 카카오톡/문자 메시지 발송 API
     * @param request 연령대, 메시지 본문
     * @return 성공 메시지
     */
    @PostMapping("/send-message")
    public ResponseEntity<String> sendMessageToAgeGroup(@RequestBody MessageRequest request) {
        int successCount = userService.sendMessageToAgeGroup(request.getAgeGroup(), request.getMessage());
        return ResponseEntity.ok(successCount + "명에게 메시지 발송 시도 완료");
    }
}
