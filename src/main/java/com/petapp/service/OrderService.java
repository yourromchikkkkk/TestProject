package com.petapp.service;

import com.petapp.dto.OrderDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface OrderService {

    OrderDto formOrder(Map<Long, Integer> map);

    OrderDto getOrderById(long id);

    List<OrderDto> getAllOrders();

    void deleteOrders();
}
