package com.petapp.service;

import com.petapp.dto.ItemDto;
import com.petapp.dto.ItemWithAmountDto;
import com.petapp.entity.Item;
import org.springframework.stereotype.Service;

@Service
public interface ItemService {

    void updateCount(long itemId, int newCount);

    Item addItem(Item itemToAdd);

    ItemDto getItemDtoById(long id);

    boolean isEnough(long itemId, int needAmount);

    void decreaseAmount(long itemId, int count);

    ItemWithAmountDto getItemWithMinPrice(String itemName, int count);
}
