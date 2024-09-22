package com.example.shopapp.response;

import com.example.shopapp.entity.Product;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductListResponse {
    private List<Product> products;
    private int totalPages;
}
