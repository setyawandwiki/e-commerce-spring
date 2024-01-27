package com.setyawandwiki.ecommerce.service;

import com.setyawandwiki.ecommerce.dto.ProductRequest;
import com.setyawandwiki.ecommerce.dto.ProductResponse;
import com.setyawandwiki.ecommerce.dto.SearchRequest;
import com.setyawandwiki.ecommerce.dto.UpdateProductResponse;
import com.setyawandwiki.ecommerce.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Page<ProductResponse> getAllProduct(SearchRequest request);
    Page<ProductResponse> getProductSeller(int page, int size);
    ProductResponse create(ProductRequest request);
    UpdateProductResponse updateProduct(Long id, ProductRequest request);
    ProductResponse getProductById(Long id);
    void deleteProduct(Long id);
}
