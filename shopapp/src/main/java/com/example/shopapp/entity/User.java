package com.example.shopapp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "users")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullname")
    private String fullName;

    @NotNull(message = "Phone number is required")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @NotBlank(message = "Password is required")
    @Column(name = "password")
    private String password;

    private String retypePassword;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "update_at")
    private Date updateAt;

    @Column(name = "is_active")
    private String isActive;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "fb_acc_id")
    private int facebookAccId;

    @Column(name = "gg_acc_id")
    private int googleAccId ;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
