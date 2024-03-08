package org.youcode.orderservice.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.youcode.orderservice.dto.OrderDto;
import org.youcode.orderservice.dto.OrderRequest;
import org.youcode.orderservice.service.OrderService;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderRest {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<Iterable<OrderDto>> getOrders() {
        return ResponseEntity.ok(orderService.getOrders());
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.createOrder(orderRequest));
    }
}
