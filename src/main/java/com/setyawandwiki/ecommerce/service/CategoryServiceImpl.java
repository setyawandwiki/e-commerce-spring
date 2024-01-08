package com.setyawandwiki.ecommerce.service;

import com.setyawandwiki.ecommerce.dto.CategoryRequest;
import com.setyawandwiki.ecommerce.dto.CategoryResponse;
import com.setyawandwiki.ecommerce.entity.Category;
import com.setyawandwiki.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;
    @Override
    public List<Category> getAllCategory() {
        List<Category> all = categoryRepository.findAll();
        return all;
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "category not found"));
        return CategoryResponse.builder()
                .name(category.getName())
                .build();
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        categoryRepository.save(category);
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    @Override
    public CategoryResponse updateCategory(CategoryRequest request, Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "category not found"));
        category.setName(request.getName());
        categoryRepository.save(category);
        return CategoryResponse.builder()
                .name(category.getName())
                .build();
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "category not found"));
        categoryRepository.delete(category);
    }
}
