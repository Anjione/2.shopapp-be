package com.example.shopapp.service.impl;

import com.example.shopapp.dto.CategoryDTO;
import com.example.shopapp.entity.Category;
import com.example.shopapp.repository.CategoryRepository;
import com.example.shopapp.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImplement implements CategoryService {

    private final CategoryRepository categoryRepository;
    @Override
    public Category getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        return category;
    }

    @Override
    public List<Category> getAllCategories(Long categoryId) {
        List<Category> listCategories = categoryRepository.findAll();
        return listCategories;
    }

    @Override
    public Category createCategory(Category category) {
        Category categoryNew = categoryRepository.save(category);
        return categoryNew;
    }

    @Override
    public Category updateCategory(Category category) {
        Category categoryExist = getCategoryById(category.getId());
        categoryExist.setName(category.getName());
        return categoryExist;
    }

    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
