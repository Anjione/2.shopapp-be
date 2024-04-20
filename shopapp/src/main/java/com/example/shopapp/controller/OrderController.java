package com.example.shopapp.controller;

import com.example.shopapp.dto.CategoryDTO;
import com.example.shopapp.dto.OrderDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    @GetMapping("")
    public ResponseEntity<String> getAllOrder(@Valid @RequestParam Long page, @RequestParam Long size) {
        return ResponseEntity.ok("hello" + page + size);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<String> getListOrder(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok("Lấy danh sách order từ userId" + userId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO orderDTO, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> error = result.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList();
                return ResponseEntity.badRequest().body(error);
            }
            return ResponseEntity.ok("hello" + "  " + orderDTO.getFullname());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrder(@Valid @PathVariable Long id,
                                              @Valid @RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok("hello update" + orderDTO.getFullname());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        return ResponseEntity.ok("hello de" + id);
    }
}
