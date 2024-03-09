package org.youcode.inventoryservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.youcode.inventoryservice.repository.InventoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public Boolean isInStock(List<String> skus) {

        return skus.stream()
                .map(s -> inventoryRepository.findBySku(s)
                                .orElseThrow(() -> new RuntimeException("Inventory not found: " + s)))
                .noneMatch(inventory -> inventory.getQuantity() <= 0);
    }
}
