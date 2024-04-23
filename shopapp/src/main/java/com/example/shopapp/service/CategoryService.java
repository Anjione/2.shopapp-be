package com.example.shopapp.service;

import com.example.shopapp.dto.CategoryDTO;
import com.example.shopapp.entity.Category;

import java.util.List;

public interface CategoryService {

    Category getCategoryById (Long categoryId);
    List<Category> getAllCategories (int pageIndex, int pageSize);
    Category createCategory (CategoryDTO categoryDTO);
    Category updateCategory (CategoryDTO categoryDTO, Long categoryId);
    String deleteCategory (Long categoryId);
}
