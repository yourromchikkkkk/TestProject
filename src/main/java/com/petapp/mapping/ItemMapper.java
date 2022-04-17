package com.petapp.mapping;

import com.petapp.dto.ItemDto;
import com.petapp.entity.Item;

import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    public ItemDto convert(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getItemName())
                .price(item.getPrice())
                .build();
    }
}
