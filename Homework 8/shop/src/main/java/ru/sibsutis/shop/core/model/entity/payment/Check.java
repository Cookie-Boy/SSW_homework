package ru.sibsutis.shop.core.model.entity.payment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "check_payment")
@PrimaryKeyJoinColumn(name = "payment_id", referencedColumnName = "id")
public class Check extends Payment {
    private String name;

    @Column(name = "bank_id")
    private String bankID;
}