package org.youcode.orderservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderDto {
    private Long id;
    private String number;
    private List<ItemDto> items;
}
