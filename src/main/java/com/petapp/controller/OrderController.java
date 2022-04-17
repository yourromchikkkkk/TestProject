package com.petapp.controller;

import com.petapp.dto.OrderDto;
import com.petapp.repository.OrderRepository;
import com.petapp.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    OrderRepository orderRepository;
    OrderService orderService;

    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable(name = "id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<OrderDto> addOrder(@RequestBody Map<Long, Integer> itemCountMapping) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.formOrder(itemCountMapping));
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteNotValidOrders() {
        orderService.deleteOrders();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
