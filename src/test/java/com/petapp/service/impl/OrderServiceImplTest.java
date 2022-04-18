package com.petapp.service.impl;

import com.petapp.dto.ItemDto;
import com.petapp.dto.OrderDto;
import com.petapp.entity.Item;
import com.petapp.entity.Order;
import com.petapp.exception.NotEnoughItemsException;
import com.petapp.exception.OrderNotFoundException;
import com.petapp.mapping.ItemMapper;
import com.petapp.mapping.OrderMapper;
import com.petapp.repository.OrderRepository;
import com.petapp.service.ItemService;
import com.petapp.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {
    @InjectMocks
    OrderServiceImpl orderService;
    @Mock
    OrderRepository orderRepository;
    @Mock
    ItemService itemService;
    @Mock
    OrderMapper orderMapper;
    @Mock
    ItemMapper itemMapper;

    @Test
    void formOrderTest() {
        Map<Long, Integer> inputMap = new HashMap<>();
        ItemDto dto = new ItemDto(1L, "iPhone 8", 333.33);
        inputMap.put(1L, 2);
        when(itemService.getItemDtoById(1L)).thenReturn(dto);
        when(itemService.isEnough(1L, 2)).thenReturn(true);
        when(orderRepository.save(any(Order.class))).thenReturn(new Order(1L, inputMap, 666.66, new Date()));
        when(orderMapper.convert(any(Order.class))).thenReturn(new OrderDto(1L, List.of(dto, dto), 666.66));
        OrderDto actual = orderService.formOrder(inputMap);

        assertEquals(666.66, actual.getSumToPay());
        assertEquals(2, actual.getItems().size());
    }

    @Test
    void formOrderThrowsExceptionTest() {
        Map<Long, Integer> inputMap = new HashMap<>();
        inputMap.put(1L, 3);
        ItemDto dto = new ItemDto(1L, "iPhone 8", 333.33);
        when(itemService.isEnough(1L, 3)).thenReturn(false);

        assertThrows(NotEnoughItemsException.class, () -> orderService.formOrder(inputMap));
    }

    @Test
    void getOrderByIdTest() {
        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(new Order(1L, Map.of(), 0.0, new Date())));

        orderService.getOrderById(1L);
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void getOrderByIdThrowsExceptionTest() {
        when(orderRepository.findById(1L))
                .thenReturn(Optional.ofNullable(null));

        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(1L));
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void getAllOrdersTest() {
        when(orderRepository.findAll())
                .thenReturn(List.of(new Order(1L, Map.of(), 0.0, new Date())));
        when(orderMapper.convert(any(Order.class))).thenReturn(new OrderDto(1L, List.of(), 0.0));
        List<OrderDto> dtos = orderService.getAllOrders();

        verify(orderMapper, times(1)).convert(any(Order.class));
        assertEquals(1, dtos.size());
        assertEquals(1L, dtos.get(0).getId());
        assertTrue(dtos.get(0).getItems().isEmpty());
        assertEquals(0.0, dtos.get(0).getSumToPay());
    }

    @Test
    void deleteOrdersTest() {
        when(orderRepository.findAll())
                .thenReturn(List.of(new Order(1L, Map.of(), 0.0, new Date(System.currentTimeMillis() - 6000*1000))));
        doNothing().when(orderRepository).deleteAll(any(List.class));

        orderService.deleteOrders();
    }
}
