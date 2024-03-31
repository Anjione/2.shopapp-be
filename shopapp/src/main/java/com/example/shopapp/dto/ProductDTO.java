package com.example.shopapp.dto;

import com.google.gson.annotations.SerializedName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    @NotBlank(message = "Title don't be empty")
    @Size(min = 3, max = 200, message = "Title must be between 3 to 200 character")
    private String name;

    private Float price;
    private String thumbnail;
    private String description;
    @SerializedName("create_at")
    private Date createAt;
    @SerializedName("update_at")
    private Date updateAt;
    @SerializedName("category_id")
    private Long categorieId;
    private MultipartFile file;
}
