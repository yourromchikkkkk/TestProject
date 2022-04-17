package com.petapp.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class OrderDto {
    private Long id;
    private List<ItemDto> items;
    private double sumToPay;
}
