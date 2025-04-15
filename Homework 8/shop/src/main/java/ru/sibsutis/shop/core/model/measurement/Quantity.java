package ru.sibsutis.shop.core.model.measurement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Quantity {
    @Embedded
    private Measurement measurement;

    @Column(name = "quantity_value")
    private Integer value;
}
