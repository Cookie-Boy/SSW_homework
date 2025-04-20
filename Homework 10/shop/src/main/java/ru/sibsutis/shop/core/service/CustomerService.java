package ru.sibsutis.shop.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sibsutis.shop.api.dto.RegisterRequestDto;
import ru.sibsutis.shop.core.model.entity.Customer;
import ru.sibsutis.shop.core.repository.CustomerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer createCustomer(RegisterRequestDto request, String externalId) {
        Customer customer = new Customer(null,
                externalId,
                request.getUsername(),
                request.getAddress(),
                List.of());
        return customerRepository.save(customer);
    }

    public Customer findCustomerByExternalId(String externalId) {
        return customerRepository.findByExternalId(externalId);
    }
}
