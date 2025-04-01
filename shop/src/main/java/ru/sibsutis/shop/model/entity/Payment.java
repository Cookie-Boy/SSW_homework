package ru.sibsutis.shop.model.entity;

import jakarta.persistence.*;
import ru.sibsutis.shop.model.paymentType.PaymentStatus;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
public abstract class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}
