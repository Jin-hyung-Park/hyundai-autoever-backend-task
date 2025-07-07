package com.autoever.backend.user;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

class UserServiceMessageTest {
    @Test
    void sendMessageToAgeGroup_카카오톡_성공_테스트() {
        // given
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        UserService userService = Mockito.spy(new UserService(userRepository, null, restTemplate));
        User user = User.builder()
                .id(1L)
                .name("홍길동")
                .regNo("900101-1234567")
                .phone("010-1234-5678")
                .build();
        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        Mockito.when(restTemplate.postForEntity(anyString(), any(), eq(String.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        // when
        int result = userService.sendMessageToAgeGroup("30대", "테스트 메시지");

        // then
        assertEquals(1, result);
    }
}
