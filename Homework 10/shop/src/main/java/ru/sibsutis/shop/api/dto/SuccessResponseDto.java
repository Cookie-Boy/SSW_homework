package ru.sibsutis.shop.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuccessResponseDto {
    private long id;
    private String message;
}
