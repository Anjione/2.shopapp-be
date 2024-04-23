package com.example.shopapp.service.impl;

import com.example.shopapp.dto.CategoryDTO;
import com.example.shopapp.entity.Category;
import com.example.shopapp.repository.CategoryRepository;
import com.example.shopapp.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ICategoryService implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final ModelMapper mapper;
    @Override
    public Category getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        return category;
    }

    @Override
    public List<Category> getAllCategories(int pageIndex, int pageSize) {
//        Pageable pageable = new PageRequest(pageIndex, pageSize, new Sort());
        List<Category> listCategories = categoryRepository.findAll();
        return listCategories;
    }

    @Override
    public Category createCategory(CategoryDTO categoryDTO) {
        Category category = mapper.map(categoryDTO, Category.class);
        category = categoryRepository.save(category);
        return category;
    }

    @Override
    public Category updateCategory(CategoryDTO categoryDTO, Long id) {
        Category categoryExist = getCategoryById(id);
        if (categoryExist != null) {
            categoryExist.setName(categoryDTO.getName());
            categoryExist = categoryRepository.save(categoryExist);
        }
        return categoryExist;
    }

    @Override
    public String deleteCategory(Long categoryId) {
        String message = "";
        try {
            categoryRepository.deleteById(categoryId);
            message = "Xóa danh mục thành công";

        } catch (Exception e) {
            message = e.getMessage();
        }
        return message;
    }
}
