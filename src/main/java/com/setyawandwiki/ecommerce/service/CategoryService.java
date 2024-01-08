package com.setyawandwiki.ecommerce.service;

import com.setyawandwiki.ecommerce.dto.CategoryRequest;
import com.setyawandwiki.ecommerce.dto.CategoryResponse;
import com.setyawandwiki.ecommerce.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategory();
    CategoryResponse getCategoryById(Long id);
    CategoryResponse createCategory(CategoryRequest request);
    CategoryResponse updateCategory(CategoryRequest request, Long id);
    void deleteCategory(Long id);
}
