package com.petapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petapp.dto.OrderDto;
import com.petapp.service.OrderService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {
    private final String orderLink = "/orders";
    private MockMvc mockMvc;
    @InjectMocks
    OrderController orderController;
    @Mock
    OrderService orderService;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    @SneakyThrows
    void getAllOrdersTest() {
        when(orderService.getAllOrders()).thenReturn(List.of(new OrderDto(1L, List.of(), 0.0)));

        mockMvc.perform(get(orderLink).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    @SneakyThrows
    void getOrderByIdTest() {
        when(orderService.getOrderById(1L)).thenReturn(new OrderDto(1L, List.of(), 0.0));

        mockMvc.perform(get(orderLink + "/1").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        verify(orderService, times(1)).getOrderById(1L);
    }

    @Test
    @SneakyThrows
    void createOrderTest() {
        ObjectMapper mapper = new ObjectMapper();
        when(orderService.formOrder(Map.of())).thenReturn(new OrderDto(1L, List.of(), 0.0));

        mockMvc.perform(post(orderLink + "/create").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(Map.of())))
                .andDo(print())
                .andExpect(status().isCreated());
        verify(orderService, times(1)).formOrder(Map.of());
    }

    @Test
    @SneakyThrows
    void deleteOrderTest() {
        mockMvc.perform(delete(orderLink + "/delete"))
                .andExpect(status().isAccepted());
    }
}
