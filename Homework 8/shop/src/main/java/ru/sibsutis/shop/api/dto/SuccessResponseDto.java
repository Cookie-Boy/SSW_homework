package ru.sibsutis.shop.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuccessResponseDto {
    private Long id;
    private String message;
}
