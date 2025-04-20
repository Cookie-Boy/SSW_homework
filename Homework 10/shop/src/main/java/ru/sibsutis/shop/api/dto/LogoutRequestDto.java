package ru.sibsutis.shop.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogoutRequestDto {
    private String refreshToken;
}