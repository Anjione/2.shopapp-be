package com.example.shopapp.controller;

import com.example.shopapp.dto.CategoryDTO;
import com.example.shopapp.dto.OrderDetailDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orderDetails")
public class OrderDetailController {
    @GetMapping("")
    public ResponseEntity<String> getAllOrderDetail(@Valid @RequestParam Long page, @RequestParam Long size) {
        return ResponseEntity.ok("hello" + page + size);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<String> getListOrderDetail(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok("Lấy danh sách order từ userId" + userId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createOrderDetail(@Valid @RequestBody OrderDetailDTO orderDetailDTO, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> error = result.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList();
                return ResponseEntity.badRequest().body(error);
            }
            return ResponseEntity.ok("hello" + "  ");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrderDetail(@Valid @PathVariable Long id,
                                              @Valid @RequestBody OrderDetailDTO orderDetailDTO) {
        return ResponseEntity.ok("hello update");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteOrderDetail(@PathVariable Long id) {
        return ResponseEntity.ok("hello de" + id);
    }
}
