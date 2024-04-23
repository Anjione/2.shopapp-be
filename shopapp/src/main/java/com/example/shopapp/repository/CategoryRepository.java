package com.example.shopapp.repository;

import com.example.shopapp.entity.Category;
import com.example.shopapp.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
//    Page<Category> findAll(Pageable pageable);
}
