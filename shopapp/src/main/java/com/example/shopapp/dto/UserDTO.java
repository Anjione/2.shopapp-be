package com.example.shopapp.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @JsonProperty("full_name")
    private String fullName;

    @NotNull(message = "Phone number is required")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("address")
    private String address;

    @NotBlank(message = "Password is required")
    @JsonProperty("password")
    private String password;

    @JsonProperty("retype_password")
    private String retypePassword;

    @JsonProperty("create_at")
    private Date createAt;

    @JsonProperty("update_at")
    private Date updateAt;

    @JsonProperty("is_active")
    private String isActive;

    @JsonProperty("dob")
    private Date dob;

    @JsonProperty("fb_acc_id")
    private int facebookAccId;

    @JsonProperty("gg_acc_id")
    private int googleAccId ;

    @NotNull(message = "RoleId is required")
    @JsonProperty("role_id")
    private Long roleId;
}
