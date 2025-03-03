package ru.sibsutis.petstore.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pet {
    private Long id;
    private String name;
    private Category category;
    private List<Tag> tags;
    private Status status;
}
