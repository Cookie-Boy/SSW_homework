package ru.sibsutis.shop.api.dto;

import lombok.Data;

@Data
public class ItemRequestDto {
    private ShippingWeightDto shippingWeight;
    private String description;
}
