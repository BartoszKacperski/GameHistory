package com.rolnik.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class UserRequest {
    private String login;
    private String email;
    private String password;
}
