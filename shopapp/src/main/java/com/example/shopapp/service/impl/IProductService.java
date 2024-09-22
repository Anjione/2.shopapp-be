package com.example.shopapp.service.impl;

import com.example.shopapp.dto.ProductDTO;
import com.example.shopapp.dto.ProductImageDTO;
import com.example.shopapp.entity.Category;
import com.example.shopapp.entity.Product;
import com.example.shopapp.entity.ProductImage;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.exception.InvalidParamException;
import com.example.shopapp.repository.CategoryRepository;
import com.example.shopapp.repository.ProductImageRepository;
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

    private final ProductImageRepository productImageRepository;

    private final ModelMapper mapper;

    @Override
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                                                .orElseThrow(() -> new DataNotFoundException("Khong tim thay danh muc yeu cau"));
        Product newProduct = mapper.map(productDTO, Product.class);
        newProduct.setCategory(category);
        newProduct = productRepository.save(newProduct);
        return newProduct;
    }

    @Override
    public Product getProductById(Long id) throws DataNotFoundException {
        Product product = productRepository.findById(id)
                                            .orElseThrow(() -> new DataNotFoundException("Khong tim thay san pham voi id " + id));
        return product;
    }

    @Override
    public Page<Product> getAllProduct(PageRequest pageRequest) {
        Page<Product> listProduct = productRepository.findAll(pageRequest);
        return listProduct;
    }

    @Override
    public Product updateProduct(Long id, ProductDTO productDTO) throws DataNotFoundException {
        Product existProduct = getProductById(id);
        if (existProduct != null) {
            Category category = categoryRepository.findById(productDTO.getCategoryId())
                                                    .orElseThrow(() -> new DataNotFoundException("Khong tim thay danh muc yeu cau"));
            existProduct.setName(productDTO.getName());
            existProduct.setCategory(category);
            existProduct.setPrice(productDTO.getPrice());
            existProduct.setDescription(productDTO.getDescription());
            existProduct.setThumbnail(productDTO.getThumbnail());
            existProduct = productRepository.save(existProduct);
        }
        return existProduct;
    }

    @Override
    public String deleteProduct(Long productId) {
        String message = "";
        try {
            Product product = productRepository.findById(productId)
                                                .orElse(null);
            if (product != null) {
                productRepository.deleteById(productId);
                message = "Xóa sản phẩm" + product.getName() + "thành công";
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
        return productRepository.existsByName(name);
    }

    public ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO) throws Exception {
        Product existProduct = productRepository.findById(productId)
                                                .orElseThrow(() -> new DataNotFoundException("Khong tim thay san pham voi id " + productImageDTO.getProductId()));
        ProductImage newProductImage = mapper.map(productImageDTO, ProductImage.class);
        newProductImage.setProduct(existProduct);
        //Khong cho insert qua 5 anh
        int sizeOfImage = productImageRepository.findByProductId(productId).size();
        if (sizeOfImage > 5) {
            throw new InvalidParamException("Number of image must be < 5");
        }
        newProductImage = productImageRepository.save(newProductImage);
        return newProductImage;
    }


}
