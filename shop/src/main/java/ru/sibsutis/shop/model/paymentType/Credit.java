package ru.sibsutis.shop.model.paymentType;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.sibsutis.shop.model.entity.Payment;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "credit_payment")
public class Credit extends Payment {
    private String number;
    private String type;
    private LocalDateTime expDate;
}