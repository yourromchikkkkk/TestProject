package com.petapp.controller;

import com.petapp.dto.ItemDto;
import com.petapp.dto.ItemWithAmountDto;
import com.petapp.entity.Item;
import com.petapp.repository.ItemRepository;
import com.petapp.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/items")
public class ItemController {
    ItemService itemService;
    ItemRepository itemRepository;

    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        return ResponseEntity.status(HttpStatus.OK).body(itemRepository.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<Item> addItem(@RequestBody Item itemToAdd) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.addItem(itemToAdd));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> getItemById(@PathVariable(name = "id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body(itemService.getItemDtoById(id));
    }

    @GetMapping("/cheapest/{name}")
    public ResponseEntity<ItemWithAmountDto> getItemWithMinPrice(@PathVariable(name = "name") String name,
                                                                 @RequestParam int count) {
        return ResponseEntity.status(HttpStatus.OK).body(itemService.getItemWithMinPrice(name, count));
    }

}
