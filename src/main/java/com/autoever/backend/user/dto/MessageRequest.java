package com.autoever.backend.user.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 연령대별 메시지 발송 요청 DTO
 */
@Getter
@Setter
public class MessageRequest {
    private String ageGroup; // 예: "20대", "30대", "40대" 등
    private String message;  // 본문 메시지(첫줄 자동 생성)
}
