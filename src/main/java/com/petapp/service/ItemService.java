package com.petapp.service;

import com.petapp.dto.ItemDto;
import com.petapp.dto.ItemWithAmountDto;
import com.petapp.entity.Item;
import org.springframework.stereotype.Service;

@Service
public interface ItemService {

    Item getItemById(long id);

    void updateCount(long itemId, int newCount);

    Item addItem(Item itemToAdd);

    ItemDto getById(long id);

    boolean isEnough(long itemId, int needAmount);

    void increaseAmount(long itemId, int count);

    void decreaseAmount(long itemId, int count);

    ItemWithAmountDto getItemWithMinPrice(String itemName, int count);
}
