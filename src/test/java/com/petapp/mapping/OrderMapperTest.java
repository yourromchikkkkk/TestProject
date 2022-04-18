package com.petapp.mapping;

import com.petapp.dto.ItemDto;
import com.petapp.dto.OrderDto;
import com.petapp.entity.Item;
import com.petapp.entity.Order;
import com.petapp.service.ItemService;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderMapperTest {
    @InjectMocks
    OrderMapper mapper;
    @Mock
    ItemService itemService;

    @Test
    void convertTest() {
        Map<Long, Integer> expectedMap = new HashMap<>();
        expectedMap.put(1L, 1);
        Order expected = new Order(1L, expectedMap, 0.0, new Date());
        OrderDto actual = mapper.convert(expected);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getItems().size(), actual.getItems().size());
        assertEquals(expected.getSumToPay(), actual.getSumToPay());
    }
}
