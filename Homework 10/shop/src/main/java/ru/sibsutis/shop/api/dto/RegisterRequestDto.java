package ru.sibsutis.shop.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.sibsutis.shop.core.model.entity.Address;

@Data
@AllArgsConstructor
public class RegisterRequestDto {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private Address address;
}
