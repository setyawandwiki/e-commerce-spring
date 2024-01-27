package com.setyawandwiki.ecommerce.controllers;

import com.setyawandwiki.ecommerce.dto.*;
import com.setyawandwiki.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @GetMapping
    public ResponseEntity<WebResponse<List<ProductResponse>>> getAllProduct(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "paging", defaultValue = "0") Integer paging,
            @RequestParam(value = "column", defaultValue = "id") String column,
            @RequestParam(value = "sort", defaultValue = "asc") String sort
    ){
        Sort.Direction sort1 = Sort.Direction.fromString(sort);
        SearchRequest request = SearchRequest.builder()
                .name(name)
                .category(category)
                .page(paging)
                .size(size)
                .sort(sort1)
                .column(column)
                .build();
        Page<ProductResponse> allProduct = productService.getAllProduct(request);
        return ResponseEntity.ok().body(WebResponse.<List<ProductResponse>>builder()
                        .data(allProduct.getContent())
                        .paging(ProductPagingResponse.builder()
                                .currentPage(allProduct.getNumber())
                                .size(allProduct.getSize())
                                .totalPage(allProduct.getTotalPages())
                                .build())
                .build());
    }
    @GetMapping("/seller")
    public ResponseEntity<WebResponse<Page<ProductResponse>>> getAll(@RequestParam(value = "size", defaultValue = "10") Integer size,
                                                                     @RequestParam(value = "paging", defaultValue = "0") Integer paging){
        Page<ProductResponse> productSeller = productService.getProductSeller(paging, size);
        return ResponseEntity.ok().body(WebResponse.<Page<ProductResponse>>builder().data(productSeller).build());
    }
    @PostMapping
    public ResponseEntity<WebResponse<ProductResponse>> create(
            @RequestParam("image1")MultipartFile image1,
            @RequestParam("image2")MultipartFile image2,
            @RequestParam("image3")MultipartFile image3,
            @RequestParam("name")String name,
            @RequestParam("details") String details,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("price") Double price,
            @RequestParam("rate") Double rate,
            @RequestParam("category") String category,
            @RequestParam("title") String title
    ){
        ProductRequest productRequest = ProductRequest.builder()
                .image3(image3)
                .image2(image2)
                .image1(image1)
                .details(details)
                .category(category)
                .name(name)
                .price(price)
                .quantity(quantity)
                .rate(rate)
                .title(title)
                .build();
        ProductResponse productResponse = productService.create(productRequest);
        return ResponseEntity.ok().body(WebResponse.<ProductResponse>builder().data(productResponse).build());
    }
    @PatchMapping(path = "/{id}")
    public ResponseEntity<WebResponse<UpdateProductResponse>> update(@PathVariable("id") Long id,
                                                                     @RequestParam(value = "image1", required = false)MultipartFile image1,
                                                                     @RequestParam(value = "image2", required = false)MultipartFile image2,
                                                                     @RequestParam(value = "image3", required = false)MultipartFile image3,
                                                                     @RequestParam(value = "name", required = false, defaultValue = "")String name,
                                                                     @RequestParam(value = "details", required = false, defaultValue = "") String details,
                                                                     @RequestParam(value = "quantity", required = false, defaultValue = "") Integer quantity,
                                                                     @RequestParam(value = "price", required = false, defaultValue = "0") Double price,
                                                                     @RequestParam(value = "rate", required = false, defaultValue = "0") Double rate,
                                                                     @RequestParam(value = "category", required = false, defaultValue = "") String category,
                                                                     @RequestParam(value = "title", required = false, defaultValue = "") String title
    ){
        ProductRequest productRequest = ProductRequest.builder()
                .image3(image3)
                .image2(image2)
                .image1(image1)
                .details(details)
                .category(category)
                .name(name)
                .price(price)
                .quantity(quantity)
                .rate(rate)
                .title(title)
                .build();
        UpdateProductResponse productResponse = productService.updateProduct(id, productRequest);
        return ResponseEntity.ok().body(WebResponse.<UpdateProductResponse>builder().data(productResponse).build());
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<WebResponse<ProductResponse>> getProductById(@PathVariable Long id){
        ProductResponse productById = productService.getProductById(id);
        return ResponseEntity.ok().body(WebResponse.<ProductResponse>builder().data(productById).build());
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<WebResponse<String>> deleteProduct(@PathVariable("id") Long id){
        productService.deleteProduct(id);
        return ResponseEntity.ok().body(WebResponse.<String>builder().data("product success deleted").build());
    }
    @GetMapping("/images/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) throws IOException {
        Path imagePath = Paths.get("C:/belajar/springboot/images/", imageName);
        Resource resource = new UrlResource(imagePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
