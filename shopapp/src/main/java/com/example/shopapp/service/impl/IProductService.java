package com.example.shopapp.service.impl;

import com.example.shopapp.dto.ProductDTO;
import com.example.shopapp.entity.Category;
import com.example.shopapp.entity.Product;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.repository.CategoryRepository;
import com.example.shopapp.repository.ProductRepository;
import com.example.shopapp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IProductService implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final ModelMapper mapper;

    @Override
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {
        Category category = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(() -> new DataNotFoundException("Khong tim thay danh muc yeu cau"));
        Product newProduct = mapper.map(productDTO, Product.class);
        newProduct.setCategory(category);
        newProduct = productRepository.save(newProduct);
        return newProduct;
    }

    @Override
    public Product getProductById(Long id) {
        return null;
    }

    @Override
    public Page<Product> getallProduct(PageRequest pageRequest) {
        return null;
    }

    @Override
    public Product updateProduct(Long id, ProductDTO productDTO) {
        return null;
    }

    @Override
    public String deleteProduct (Long productId) {
        String message = "";
        try {
            Product product = productRepository.findById(productId).orElse(null);
            if (product != null) {
                productRepository.deleteById(productId);
                message = "Xóa sản phẩm thành công";
            } else {
                message = "Sản phẩm không tồn tại";
            }

        } catch (Exception e) {
            message = e.getMessage();
        }
        return message;
    }

    @Override
    public boolean existByName(String name) {
        return false;
    }
}
