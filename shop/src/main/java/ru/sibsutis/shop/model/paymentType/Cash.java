package ru.sibsutis.shop.model.paymentType;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.sibsutis.shop.model.entity.Payment;

@Entity
@Getter
@Setter
@Table(name = "cash_payment")
public class Cash extends Payment {
    private float cashTendered;
}
