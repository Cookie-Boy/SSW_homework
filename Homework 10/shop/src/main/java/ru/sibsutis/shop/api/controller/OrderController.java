package ru.sibsutis.shop.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.sibsutis.shop.api.dto.OrderRequestDto;
import ru.sibsutis.shop.api.dto.OrderResponseDto;
import ru.sibsutis.shop.api.dto.SuccessResponseDto;
import ru.sibsutis.shop.core.service.KeycloakService;
import ru.sibsutis.shop.core.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final KeycloakService keycloakService;

    @PreAuthorize("hasRole('REGULAR')")
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@AuthenticationPrincipal Jwt jwt,
                                                        @RequestBody OrderRequestDto orderRequestDto) {
        Long userId = keycloakService.getUserIdFromJwt(jwt);
        OrderResponseDto response = orderService.createOrder(userId, orderRequestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrders(@AuthenticationPrincipal Jwt jwt) {
        Long userId = keycloakService.getUserIdFromJwt(jwt);

        if (keycloakService.hasRole(jwt, "ADMIN")) {
            return ResponseEntity.ok(orderService.getAllOrders());
        }

        return ResponseEntity.ok(orderService.getAllOrders(userId));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<SuccessResponseDto> deleteOrder(@AuthenticationPrincipal Jwt jwt,
                                                          @PathVariable Long orderId) {
        Long userId = keycloakService.getUserIdFromJwt(jwt);

        if (keycloakService.hasRole(jwt, "ADMIN")) {
            return ResponseEntity.ok(orderService.deleteOrder(orderId));
        }

        return ResponseEntity.ok(orderService.deleteOrder(userId, orderId));
    }
}
