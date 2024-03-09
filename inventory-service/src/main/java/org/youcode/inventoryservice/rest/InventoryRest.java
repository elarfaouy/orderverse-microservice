package org.youcode.inventoryservice.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.youcode.inventoryservice.service.InventoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryRest {
    private final InventoryService inventoryService;

    @GetMapping
    public Boolean isInStock(@RequestParam List<String> skus) {
        return inventoryService.isInStock(skus);
    }
}
