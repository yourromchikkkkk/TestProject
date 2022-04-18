package com.petapp.mapping;

import com.petapp.dto.ItemWithAmountDto;
import com.petapp.entity.Item;
import com.petapp.service.ItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ItemWithAmountMapperTest {
    @InjectMocks
    ItemWithAmountMapper mapper;

    @Test
    void convertTest() {
        Item expected = new Item(1L, "iPhone 8", 333.33, 3);
        ItemWithAmountDto actual = mapper.convert(expected);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getItemName(), actual.getName());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(0, actual.getCount());
    }
}
