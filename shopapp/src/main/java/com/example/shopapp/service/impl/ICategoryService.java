package com.example.shopapp.service.impl;

import com.example.shopapp.dto.CategoryDTO;
import com.example.shopapp.entity.BaseResponse;
import com.example.shopapp.entity.Category;
import com.example.shopapp.repository.CategoryRepository;
import com.example.shopapp.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ICategoryService extends BaseResponse<Category> implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final ModelMapper mapper;

    @Override
    public BaseResponse<Category> getCategoryById(Long categoryId) {
        BaseResponse<Category> response = new BaseResponse<>("Không tìm thấy danh mục nào có id = " + categoryId, null);
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category != null) {
            response.setData(category);
            response.setMessage("Đã tìm thấy danh mục " + category.getName());
        }
        return response;
    }

    @Override
    public List<Category> getAllCategories(int pageIndex, int pageSize) {
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
        Category categoryExist = categoryRepository.findById(id).orElse(null);
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
            Category category = categoryRepository.findById(categoryId).orElse(null);
            if (category != null) {
                categoryRepository.deleteById(categoryId);
                message = "Xóa danh mục thành công";
            } else {
                message = "Danh mục không tồn tại";
            }

        } catch (Exception e) {
            message = e.getMessage();
        }
        return message;
    }
}
