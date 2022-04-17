package com.petapp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class ItemDto {
    private Long id;
    private String name;
    private double price;
}
