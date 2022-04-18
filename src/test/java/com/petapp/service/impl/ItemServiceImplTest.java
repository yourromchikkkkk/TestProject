package com.petapp.service.impl;

import com.petapp.dto.ItemWithAmountDto;
import com.petapp.entity.Item;
import com.petapp.exception.IllegalInputException;
import com.petapp.exception.ItemNotFoundException;
import com.petapp.exception.NotEnoughItemsException;
import com.petapp.mapping.ItemMapper;
import com.petapp.mapping.ItemWithAmountMapper;
import com.petapp.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {
    @InjectMocks
    ItemServiceImpl itemService;
    @Mock
    ItemRepository itemRepository;
    @Mock
    ItemMapper itemMapper;
    @Mock
    ItemWithAmountMapper itemWithAmountMapper;

    @Test
    void getItemDtoByIdTest() {
        when(itemRepository.findItemById(1L)).thenReturn(Optional.of(new Item(1L, "iPhone 8", 333.33, 3)));

        itemService.getItemDtoById(1L);
        verify(itemRepository).findItemById(1L);
    }

    @Test
    void getItemDtoByIdThrowsExceptionTest() {
        when(itemRepository.findItemById(1L)).thenReturn(Optional.ofNullable(null));

        assertThrows(ItemNotFoundException.class, () -> itemService.getItemDtoById(1L));
        verify(itemRepository).findItemById(1L);
    }

    @Test
    void isEnoughTest() {
        when(itemRepository.findItemById(1L)).thenReturn(Optional.of(new Item(1L, "iPhone 8", 333.33, 3)));

        assertTrue(itemService.isEnough(1,1));
        assertTrue(itemService.isEnough(1,3));
        assertFalse(itemService.isEnough(1,5));
        verify(itemRepository, times(3)).findItemById(1L);
    }

    @Test
    void decreaseAmountTest() {
        when(itemRepository.findItemById(1L)).thenReturn(Optional.of(new Item(1L, "iPhone 8", 333.33, 3)));

        when(itemRepository.save(any(Item.class))).thenReturn(null);
        itemService.decreaseAmount(1, 2);
        verify(itemRepository, times(1)).save(any(Item.class));
    }

    @Test
    void decreaseAmountThrowsExceptionTest() {
        when(itemRepository.findItemById(1L)).thenReturn(Optional.of(new Item(1L, "iPhone 8", 333.33, 3)));

        assertThrows(NotEnoughItemsException.class, () -> itemService.decreaseAmount(1, 5));
        verify(itemRepository, times(1)).findItemById(1L);
    }

    @Test
    void updateCountTest() {
        when(itemRepository.findItemById(1L)).thenReturn(Optional.of(new Item(1L, "iPhone 8", 333.33, 3)));

        itemService.updateCount(1, 10);
        verify(itemRepository, times(1)).findItemById(1L);
    }

    @Test
    void getItemWithMinPriceTest() {
        Item expected = new Item(1L, "iPhone 8", 333.33, 3);
        when(itemRepository.findItemsByItemNameWithMinPrice("iPhone")).thenReturn(expected);
        when(itemWithAmountMapper.convert(any(Item.class))).thenReturn(new ItemWithAmountDto(1L, "iPhone 8", 3, 333.33));
        when(itemRepository.findItemById(1L)).thenReturn(Optional.of(new Item(1L, "iPhone 8", 333.33, 3)));
        ItemWithAmountDto actual = itemService.getItemWithMinPrice("iPhone", 3);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getItemName(), actual.getName());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(3, actual.getCount());
    }

    @Test
    void getItemWithMinPriceWithNotEnoughAmountTest() {
        Item expected = new Item(1L, "iPhone 8", 333.33, 1);
        when(itemRepository.findItemsByItemNameWithMinPrice("iPhone")).thenReturn(expected);
        when(itemWithAmountMapper.convert(any(Item.class))).thenReturn(new ItemWithAmountDto(1L, "iPhone 8", 3, 333.33));
        when(itemRepository.findItemById(1L)).thenReturn(Optional.of(new Item(1L, "iPhone 8", 333.33, 3)));
        ItemWithAmountDto actual = itemService.getItemWithMinPrice("iPhone", 3);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getItemName(), actual.getName());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(1, actual.getCount());
    }

    @Test
    void getItemWithMinPriceWithZeroAmountTest() {
        Item expected = new Item(1L, "iPhone 8", 333.33, 0);
        when(itemRepository.findItemsByItemNameWithMinPrice("iPhone")).thenReturn(expected);
        when(itemWithAmountMapper.convert(any(Item.class))).thenReturn(new ItemWithAmountDto(1L, "iPhone 8", 3, 333.33));
        ItemWithAmountDto actual = itemService.getItemWithMinPrice("iPhone", 3);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getItemName(), actual.getName());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(0, actual.getCount());
    }

    @Test
    void addItemTest() {
        Item expected = new Item(1L, "iPhone 8", 333.33, 0);
        when(itemRepository.save(expected)).thenReturn(expected);

        itemService.addItem(expected);
        verify(itemRepository, times(1)).save(expected);
    }

    @Test
    void addItemThrowsExceptionTest() {
        Item expected = new Item(1L, "iPhone 8", 333.33, -1);

        assertThrows(IllegalInputException.class, () -> itemService.addItem(expected));
        verify(itemRepository, times(0)).save(expected);
    }
}
