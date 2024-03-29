package com.rolnik.shop.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class UserCreateRequest {
    private String username;
    private String email;
    private String password;
}
