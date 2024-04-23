package com.example.shopapp.controller;

import com.example.shopapp.dto.CategoryDTO;
import com.example.shopapp.entity.BaseResponse;
import com.example.shopapp.entity.Category;
import com.example.shopapp.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("getCategory/{id}")
    public ResponseEntity<BaseResponse<Category>> getCategory(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }
    @GetMapping("getAllCategory")
    public ResponseEntity<List<Category>> getAllCategory(@RequestParam int page, @RequestParam int size) {
        List<Category> categoryList = categoryService.getAllCategories(page, size);
        return ResponseEntity.ok(categoryList);
    }

    @PostMapping("createCategory")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> error = result.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList();
            return ResponseEntity.badRequest().body(error);
        }
        return ResponseEntity.ok(categoryService.createCategory(categoryDTO));
    }

    @PutMapping("updateCategory/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> error = result.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList();
            return ResponseEntity.badRequest().body(error);
        }
        return ResponseEntity.ok(categoryService.updateCategory(categoryDTO, id));
    }

    @DeleteMapping("deleteCategory/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }
}
