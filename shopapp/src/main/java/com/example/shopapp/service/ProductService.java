package com.example.shopapp.service;

import com.example.shopapp.dto.ProductDTO;
import com.example.shopapp.entity.Product;
import com.example.shopapp.exception.DataNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Product createProduct(ProductDTO productDTO) throws DataNotFoundException;

    Product getProductById(Long id);

    Page<Product> getallProduct(PageRequest pageRequest);

    Product updateProduct(Long id, ProductDTO productDTO);

    String deleteProduct (Long productId);

    boolean existByName(String name);
}
