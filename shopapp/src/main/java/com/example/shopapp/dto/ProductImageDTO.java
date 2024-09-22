package com.example.shopapp.dto;

import com.example.shopapp.entity.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;


@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageDTO {

    @JsonProperty("image_url")
    @Size(min = 5, max = 200, message = "Image's name")
    private String imgUrl;

    @JsonProperty("product_id")
    private Long productId;
}
