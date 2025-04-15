package ru.sibsutis.shop.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sibsutis.shop.api.dto.ItemRequestDto;
import ru.sibsutis.shop.api.mapper.ItemMapper;
import ru.sibsutis.shop.core.model.entity.Item;
import ru.sibsutis.shop.core.repository.ItemRepository;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private final ItemMapper itemMapper;

    public Item createItem(ItemRequestDto itemDto) {
        Item item = itemMapper.toEntity(itemDto);
        return itemRepository.save(item);
    }

}
