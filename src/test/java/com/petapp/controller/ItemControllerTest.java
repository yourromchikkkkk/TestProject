package com.petapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petapp.dto.ItemDto;
import com.petapp.dto.ItemWithAmountDto;
import com.petapp.entity.Item;
import com.petapp.repository.ItemRepository;
import com.petapp.service.ItemService;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {
    private final String itemLink = "/items";
    private MockMvc mockMvc;
    @InjectMocks
    ItemController itemController;
    @Mock
    ItemService itemService;
    @Mock
    ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
    }

    @Test
    @SneakyThrows
    void getAllItemsTest() {
        when(itemRepository.findAll()).thenReturn(List.of(new Item(1L, "iPhone 8", 333.33, 3),
                new Item(2L, "iPhone X", 444.44, 9),
                new Item(3L, "iPhone XS MAX", 666.66, 7),
                new Item(4L, "iPhone SE2", 555.55, 0)));

        mockMvc.perform(get(itemLink).accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());
        verify(itemRepository, times(1)).findAll();
    }

    @Test
    @SneakyThrows
    void getByIdTest() {
        when(itemService.getItemDtoById(1L)).thenReturn(new ItemDto(1L, "iPhone 8", 333.33));

        mockMvc.perform(get(itemLink +"/1").accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());

        verify(itemService,times(1)).getItemDtoById(1L);
    }

    @Test
    @SneakyThrows
    void getItemWithMinPriceTest(){
        when(itemService.getItemWithMinPrice(anyString(), anyInt()))
                .thenReturn(new ItemWithAmountDto(1L, "iPhone 8",3, 333.33));

        mockMvc.perform(get(itemLink + "/cheapest/iPhone?count=3").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(itemService, times(1)).getItemWithMinPrice(anyString(), anyInt());
    }

    @Test
    @SneakyThrows
    void addItemTest() {
        ObjectMapper mapper = new ObjectMapper();
        when(itemService.addItem(any(Item.class))).thenReturn(any(Item.class));

        mockMvc.perform(post(itemLink + "/create").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new Item(1L, "iPhone 8", 333.33, 3))))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(itemService, times(1)).addItem(any(Item.class));
    }
}
