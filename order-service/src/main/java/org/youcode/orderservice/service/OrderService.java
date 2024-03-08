package org.youcode.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.youcode.orderservice.dto.OrderDto;
import org.youcode.orderservice.dto.ItemDto;
import org.youcode.orderservice.dto.OrderRequest;
import org.youcode.orderservice.entity.Order;
import org.youcode.orderservice.entity.Item;
import org.youcode.orderservice.entity.OrderItem;
import org.youcode.orderservice.repository.OrderItemRepository;
import org.youcode.orderservice.repository.OrderRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@EnableDiscoveryClient
public class OrderService {
    private final RestClient.Builder restClient;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderDto createOrder(OrderRequest orderRequest) {
        List<Item> items = orderRequest.getItems().stream()
                .map(id -> orderItemRepository.findById(id).orElseThrow(() -> new RuntimeException("Order item not found: " + id)))
                .toList();

        List<OrderItem> orderItems = items.stream()
                .map(item -> OrderItem.builder()
                        .item(item)
                        .build()
                )
                .toList();

        Order order = Order.builder()
                .number(UUID.randomUUID().toString())
                .items(orderItems)
                .build();

        orderItems.forEach(orderItem -> orderItem.setOrder(order));

        Boolean isInStock = restClient.build().get().uri("http://inventory-service/api/v1/inventory", uriBuilder -> uriBuilder.queryParam("skus", items.stream().map(Item::getSku).toList()).build())
                .retrieve()
                .body(Boolean.class);

        if (Boolean.FALSE.equals(isInStock)) {
            throw new RuntimeException("Out of stock");
        }

        Order saved = orderRepository.save(order);

        return mapToDto(saved);
    }

    public Iterable<OrderDto> getOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    private OrderDto mapToDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .number(order.getNumber())
                .items(
                        order.getItems().stream()
                                .map(orderItem -> mapToDto(orderItem.getItem()))
                                .toList()
                )
                .build();
    }

    private ItemDto mapToDto(Item item) {
        return ItemDto.builder()
                .sku(item.getSku())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .build();
    }
}
