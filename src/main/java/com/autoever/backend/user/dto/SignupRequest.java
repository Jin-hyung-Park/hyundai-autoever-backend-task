package com.autoever.backend.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    private String account;
    private String password;
    private String name;
    private String regNo;
    private String phone;
    private String address;
}
