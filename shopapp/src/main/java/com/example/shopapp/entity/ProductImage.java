package com.example.shopapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_image")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "img_url", nullable = false)
    private String imgUrl;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
