package org.youcode.orderservice.rest;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.youcode.orderservice.dto.OrderDto;
import org.youcode.orderservice.dto.OrderRequest;
import org.youcode.orderservice.service.OrderService;

import java.util.concurrent.CompletableFuture;

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
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
//    @Retry(name = "inventory")
    public CompletableFuture<ResponseEntity<?>> createOrder(@RequestBody OrderRequest orderRequest) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(orderService.createOrder(orderRequest)));
    }

    public CompletableFuture<ResponseEntity<?>> fallbackMethod(OrderRequest orderRequest, Throwable throwable) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.badRequest().body("Service is down, please try again later, " +  throwable.getMessage()));
    }
}
