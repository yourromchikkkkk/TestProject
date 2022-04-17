package com.petapp.service.impl;

import com.petapp.dto.ItemDto;
import com.petapp.dto.OrderDto;
import com.petapp.entity.Order;
import com.petapp.exception.NotEnoughItemsException;
import com.petapp.exception.OrderNotFoundException;
import com.petapp.exception.constant.ErrorMessage;
import com.petapp.mapping.ItemMapper;
import com.petapp.mapping.OrderMapper;
import com.petapp.repository.ItemRepository;
import com.petapp.repository.OrderRepository;
import com.petapp.service.ItemService;
import com.petapp.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;
    ItemRepository itemRepository;
    ItemService itemService;
    OrderMapper orderMapper;
    ItemMapper itemMapper;

    private Order addOrder(Order order) {
        log.info("Order was added");
        return orderRepository.save(order);
    }

    @Override
    public OrderDto formOrder(Map<Long, Integer> itemCountMapping) {
        Order order = new Order();
        double sumToPay = 0;
        order.setItems(itemCountMapping);
        for (Map.Entry<Long, Integer> elem : itemCountMapping.entrySet()) {
            if (!itemService.isEnough(elem.getKey(), elem.getValue())) {
                throw new NotEnoughItemsException(ErrorMessage.NOT_ENOUGH_ITEMS + elem.getKey());
            }
            for(int index = 0; index < elem.getValue(); index++) {
                ItemDto item = itemService.getById(elem.getKey());
                sumToPay += item.getPrice();
            }
            itemService.decreaseAmount(elem.getKey(),elem.getValue());
        }
        order.setSumToPay(sumToPay);
        return orderMapper.convert(addOrder(order));
    }

    @Override
    public OrderDto getOrderById(long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            return orderMapper.convert(optionalOrder.get());
        }
        throw new OrderNotFoundException(ErrorMessage.ORDER_NOT_FOUND);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> orderMapper.convert(order))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOrders() {
        orderRepository.deleteAll(orderRepository.findAll().stream()
                .filter((order -> order.getCreationTime().getTime() < (System.currentTimeMillis() - 600*1000)))
                .collect(Collectors.toList()));
        log.info("Not valid orders deleted");
    }
}
