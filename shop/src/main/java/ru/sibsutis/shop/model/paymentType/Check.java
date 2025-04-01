package ru.sibsutis.shop.model.paymentType;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.sibsutis.shop.model.entity.Payment;

@Entity
@Getter
@Setter
@Table(name = "check_payment")
public class Check extends Payment {
    private String name;
    private String bankID;
}