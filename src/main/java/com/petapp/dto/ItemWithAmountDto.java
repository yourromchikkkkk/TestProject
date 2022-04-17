package com.petapp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class ItemWithAmountDto {
    private long id;
    private String name;
    private int count;
    private double price;
}
