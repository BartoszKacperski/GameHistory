package com.rolnik.shop.dtos.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PlayerUpdateRequest {
    private Long id;
    private String nickname;
}
