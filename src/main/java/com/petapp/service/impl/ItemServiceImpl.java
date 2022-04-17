package com.petapp.service.impl;

import com.petapp.dto.ItemDto;
import com.petapp.dto.ItemWithAmountDto;
import com.petapp.entity.Item;
import com.petapp.exception.ItemNotFoundException;
import com.petapp.exception.IllegalInputException;
import com.petapp.exception.NotEnoughItemsException;
import com.petapp.exception.constant.ErrorMessage;
import com.petapp.mapping.ItemMapper;
import com.petapp.mapping.ItemWithAmountMapper;
import com.petapp.repository.ItemRepository;
import com.petapp.service.ItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {
    ItemRepository itemRepository;
    ItemMapper itemMapper;
    ItemWithAmountMapper itemWithAmountMapper;

    private Item getItemById(long id) {
        Optional<Item> optionalItem = itemRepository.findItemById(id);
        if (optionalItem.isPresent()) {
            return optionalItem.get();
        }
        throw new ItemNotFoundException(ErrorMessage.ITEM_NOT_FOUND);
    }
    @Override
    public ItemDto getById(long id) {
        return itemMapper.convert(getItemById(id));
    }

    @Override
    public boolean isEnough(long itemId, int needAmount) {
        return getItemById(itemId).getAvailableAmount() >= needAmount;
    }

    @Override
    public void increaseAmount(long itemId, int count) {
        Item itemToUpdate = getItemById(itemId);
        itemToUpdate.setAvailableAmount(itemToUpdate.getAvailableAmount() + count);
        itemRepository.save(itemToUpdate);
        log.info("Item amount with id = " + itemId + " was updated");
    }

    @Override
    public void decreaseAmount(long itemId, int count) {
        Item itemToUpdate = getItemById(itemId);
        itemToUpdate.setAvailableAmount(itemToUpdate.getAvailableAmount() - count);
        if (itemToUpdate.getAvailableAmount() < 0) {
            throw new NotEnoughItemsException(ErrorMessage.NOT_ENOUGH_ITEMS);
        }
        itemRepository.save(itemToUpdate);
        log.info("Item amount with id = " + itemId + " was updated");
    }

    private void updateCount(long itemId, int newCount) {
        Item itemToUpdate = getItemById(itemId);
        itemToUpdate.setAvailableAmount(newCount);
        itemRepository.save(itemToUpdate);
        log.info("Item amount with id = " + itemId + " was updated");
    }

    @Override
    public ItemWithAmountDto getItemWithMinPrice(String itemName, int count) {
        Item item = itemRepository.findItemsByItemNameWithMinPrice(itemName);
        ItemWithAmountDto returnItem = itemWithAmountMapper.convert(item);
        if (item.getAvailableAmount() == 0) {
            returnItem.setCount(0);
        } else if (item.getAvailableAmount() >= count) {
            returnItem.setCount(count);
            updateCount(item.getId(), item.getAvailableAmount() - count);
        } else {
            returnItem.setCount(item.getAvailableAmount());
            updateCount(item.getId(), 0);
        }
        return returnItem;
    }


    @Override
    public Item addItem(Item itemToAdd) {
        if (itemToAdd.getPrice() > 0 && itemToAdd.getAvailableAmount() > 0) {
            log.info("item was added");
            return itemRepository.save(itemToAdd);
        }
        throw new IllegalInputException(ErrorMessage.WRONG_ITEM_INPUT);
    }
}
