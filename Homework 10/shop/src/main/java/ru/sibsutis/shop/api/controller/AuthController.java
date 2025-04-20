package ru.sibsutis.shop.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sibsutis.shop.api.dto.*;
import ru.sibsutis.shop.core.service.KeycloakService;
import ru.sibsutis.shop.core.service.CustomerService;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final CustomerService customerService;
    private final KeycloakService keycloakService;

    @PostMapping("/register")
    public ResponseEntity<SuccessResponseDto> registerUser(@RequestBody RegisterRequestDto request) {
        String token = keycloakService.getAdminToken();
        String keycloakId = keycloakService.createUser(request, token);
        customerService.createCustomer(request, keycloakId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponseDto(200, "You have been successfully registered!"));
    }

    @PostMapping("/login")
    public ResponseEntity<SessionResponseDto> authorizeUser(@RequestBody AuthRequestDto request) {
        SessionResponseDto session = keycloakService.authenticateUser(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(session);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> deauthorizeUser(@RequestBody LogoutRequestDto dto) {
        keycloakService.terminate(dto.getRefreshToken());
        return ResponseEntity.ok().build();
    }
}
