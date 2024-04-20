package com.example.shopapp.service;

import com.example.shopapp.dto.CategoryDTO;
import com.example.shopapp.entity.Category;

import java.util.List;

public interface CategoryService {

    Category getCategoryById (Long categoryId);
    List<Category> getAllCategories (Long categoryId);
    Category createCategory (Category category);
    Category updateCategory (Category category);
    void deleteCategory (Long categoryId);
}
