package ru.sibsutis.shop.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SessionResponseDto {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private int expiresIn;
    private int refreshExpiresIn;
}
