package com.setyawandwiki.ecommerce.controllers;

import com.setyawandwiki.ecommerce.dto.CategoryRequest;
import com.setyawandwiki.ecommerce.dto.CategoryResponse;
import com.setyawandwiki.ecommerce.dto.WebResponse;
import com.setyawandwiki.ecommerce.entity.Category;
import com.setyawandwiki.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping
    public ResponseEntity<WebResponse<List<Category>>> getAllCategory(){
        List<Category> allCategory = categoryService.getAllCategory();
        return ResponseEntity.ok().body(WebResponse.<List<Category>>builder().data(allCategory).build());
    }
    @PostMapping
    public ResponseEntity<WebResponse<CategoryResponse>> createCategory(@RequestBody CategoryRequest request){
        CategoryResponse category = categoryService.createCategory(request);
        return ResponseEntity.ok().body(WebResponse.<CategoryResponse>builder().data(category).build());
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<WebResponse<CategoryResponse>> categoryById(@PathVariable("id") Long id){
        CategoryResponse categoryById = categoryService.getCategoryById(id);
        return ResponseEntity.ok().body(WebResponse.<CategoryResponse>builder().data(categoryById).build());
    }
    @PatchMapping(path = "/{id}")
    public ResponseEntity<WebResponse<CategoryResponse>> updateCategory(@RequestBody CategoryRequest request, @PathVariable Long id){
        CategoryResponse categoryResponse = categoryService.updateCategory(request, id);
        return ResponseEntity.ok().body(WebResponse.<CategoryResponse>builder().data(categoryResponse).build());
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<WebResponse<String>> deleteCategory(@PathVariable("id") Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().body(WebResponse.<String>builder().data("success delete category").build());
    }
}
