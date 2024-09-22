package com.example.shopapp.controller;

import com.example.shopapp.dto.ProductDTO;
import com.example.shopapp.dto.ProductImageDTO;
import com.example.shopapp.entity.Product;
import com.example.shopapp.entity.ProductImage;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.service.ProductService;
import com.github.javafaker.Faker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;
    @GetMapping("")
    public ResponseEntity<List<Product>> getAllProduct(@RequestParam int page, @RequestParam int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createAt").descending());
        Page<Product> productPage = productService.getAllProduct(pageRequest);
        int totalPages = productPage.getTotalPages();
        List<Product> productList = productPage.getContent();
        return ResponseEntity.ok(productList);
    }

    @GetMapping("{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) throws IOException {
        try {
            Product product = productService.getProductById(productId);
            return ResponseEntity.ok(product);
        } catch (DataNotFoundException e) {
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "create")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO productDTO,
                                           BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> error = result.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList();
                return ResponseEntity.badRequest().body(error);
            }
            Product newProduct = productService.createProduct(productDTO);
            return ResponseEntity.ok(newProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        try {
            Product productModify = productService.updateProduct(id, productDTO);
            return ResponseEntity.ok(productModify);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        String product = productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.OK).body("Delete product successfully");
    }

    @PostMapping(value = "uploads/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile (@PathVariable Long productId, @ModelAttribute("files") List<MultipartFile> files) throws Exception {
        List<ProductImage> listImage = new ArrayList<>();
        files = files == null ? new ArrayList<MultipartFile>() : files;
        if (files.size() > 5) {
            return ResponseEntity.badRequest().body("You can only upload maximum 5 pic");
        }
        for (MultipartFile file : files) {
            if (file.getSize() == 0) {
                continue;
            }
            if (file.getSize() > 10 * 1024 *1024) {
                return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File is too large");
            }
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("File must be an image");
            }
            String filename = storeFile(file);
            ProductImage newProductImage = productService.createProductImage(productId, ProductImageDTO.builder()
                    .imgUrl(filename)
                    .build());
            listImage.add(newProductImage);
        }
        return ResponseEntity.ok().body(listImage);
    }

    private String storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
        Path uploadDir = Paths.get("uploads");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        Path destination = Paths.get(uploadDir.toString(), uniqueFileName);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }

    @PostMapping("generateFakeProduct")
    public ResponseEntity<String> generateFakeProducts() throws DataNotFoundException {
        Faker faker = new Faker();
        for (int i = 0; i < 20; i++) {
            String productName = faker.commerce().productName();
            if (productService.existByName(productName)) {
                continue;
            }
            ProductDTO productDTO = ProductDTO.builder()
                    .name(productName)
                    .price((float)faker.number().numberBetween(6, 100))
                    .description(faker.lorem().sentence())
                    .thumbnail("")
                    .categoryId((long)faker.number().numberBetween(1, 5))
                    .build();
            productService.createProduct(productDTO);
        }
        return ResponseEntity.ok("Fake product created successfully");
    }
}
