package com.autoever.backend.user.dto;

import com.autoever.backend.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String account;
    private String name;
    private String regNo;
    private String phone;
    private String address;

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
