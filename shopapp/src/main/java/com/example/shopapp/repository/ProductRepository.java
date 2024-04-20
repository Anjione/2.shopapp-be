package com.example.shopapp.repository;

import com.example.shopapp.entity.Product;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String productName);

    Page<Product> findAll(Pageable pageable);
}
