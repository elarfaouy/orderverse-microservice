package org.youcode.inventoryservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryDto {
    private Long id;
    private String sku;
    private Integer quantity;
}
