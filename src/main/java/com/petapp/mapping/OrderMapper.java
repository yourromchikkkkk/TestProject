package com.petapp.mapping;

import com.petapp.dto.ItemDto;
import com.petapp.dto.OrderDto;
import com.petapp.entity.Order;
import com.petapp.service.ItemService;
import lombok.AllArgsConstructor;


import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class OrderMapper {
    ItemService itemService;

    public OrderDto convert(Order source) {
        List<ItemDto> items = new ArrayList<>();

        for (Map.Entry<Long, Integer> elem : source.getItems().entrySet()) {
            for (int index = 0; index < elem.getValue(); index++) {
                items.add(itemService.getById(elem.getKey()));
            }
        }

        return OrderDto.builder()
                .id(source.getId())
                .items(items)
                .sumToPay(source.getSumToPay())
                .build();
    }
}
