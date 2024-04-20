package com.example.shopapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

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
    @JsonProperty("create_at")
    private Date createAt;
    @JsonProperty("update_at")
    private Date updateAt;
    @JsonProperty("category_id")
    private Long categoryId;
    private List<MultipartFile> files;
}
