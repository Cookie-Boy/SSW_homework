package ru.sibsutis.shop.core.model.measurement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Weight {
    @Embedded
    private Measurement measurement;

    @Column(name = "shipping_weight_value")
    private BigDecimal value;
}
