package com.petapp.mapping;

import com.petapp.dto.ItemWithAmountDto;
import com.petapp.entity.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemWithAmountMapper {

    public ItemWithAmountDto convert(Item source) {
        return ItemWithAmountDto.builder()
                .id(source.getId())
                .name(source.getItemName())
                .price(source.getPrice())
                .build();
    }

}
