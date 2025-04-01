package ru.sibsutis.shop.model;

import lombok.Data;
import ru.sibsutis.shop.model.entity.Payment;

import java.time.LocalDateTime;

// Критерии для поиска
@Data
public class OrderSearchCriteria {
    private Address address;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private Class<? extends Payment> payment;
    private String customerName;
    private String paymentStatus;
    private String orderStatus;
}
