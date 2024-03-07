package org.youcode.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.youcode.productservice.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
