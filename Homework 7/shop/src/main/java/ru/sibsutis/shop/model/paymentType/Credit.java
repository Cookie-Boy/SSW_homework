package ru.sibsutis.shop.model.paymentType;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.sibsutis.shop.model.entity.Payment;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "credit_payment")
@PrimaryKeyJoinColumn(name = "payment_id", referencedColumnName = "id")
public class Credit extends Payment {
    private String number;
    private String type;
    private LocalDateTime expDate;
}