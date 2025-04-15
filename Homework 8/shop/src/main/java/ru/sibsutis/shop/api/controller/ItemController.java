package ru.sibsutis.shop.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sibsutis.shop.api.dto.ItemRequestDto;
import ru.sibsutis.shop.core.model.entity.Item;
import ru.sibsutis.shop.core.service.ItemService;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody ItemRequestDto itemRequestDto) {
        Item response = itemService.createItem(itemRequestDto);
        return ResponseEntity.ok(response);
    }
}
