package org.youcode.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.youcode.orderservice.entity.Item;

@Repository
public interface OrderItemRepository extends JpaRepository<Item, Long> {
}
