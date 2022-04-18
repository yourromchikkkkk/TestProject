package com.petapp.mapping;

import com.petapp.dto.ItemDto;
import com.petapp.entity.Item;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ItemMapperTest {
    @InjectMocks
    private ItemMapper mapper;

    @Test
    void convertTest() {
        Item expected = new Item(1L, "iPhone 8", 333.33, 3);
        ItemDto actual =  mapper.convert(expected);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getItemName(), actual.getName());
        assertEquals(expected.getPrice(), actual.getPrice());
    }
}
