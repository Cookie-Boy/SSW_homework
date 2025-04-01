package ru.sibsutis.shop.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.sibsutis.shop.model.paymentType.PaymentStatus;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "payment_type")
public abstract class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}
