package org.youcode.orderservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemDto {
    private String sku;
    private Double price;
    private Integer quantity;
}
