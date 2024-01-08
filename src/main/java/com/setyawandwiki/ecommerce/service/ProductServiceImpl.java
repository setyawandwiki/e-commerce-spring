package com.setyawandwiki.ecommerce.service;

import com.setyawandwiki.ecommerce.dto.*;
import com.setyawandwiki.ecommerce.entity.Category;
import com.setyawandwiki.ecommerce.entity.Product;
import com.setyawandwiki.ecommerce.entity.Role;
import com.setyawandwiki.ecommerce.entity.User;
import com.setyawandwiki.ecommerce.repository.CategoryRepository;
import com.setyawandwiki.ecommerce.repository.ProductRepository;
import com.setyawandwiki.ecommerce.repository.UserRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    private final String FOLDER_PATH = "C:\\belajar\\springboot\\images";
    @Override
    public Page<ProductResponse> getAllProduct(SearchRequest request) {
        Sort.Direction sort1 = null;
        Specification<Product> productSpecification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(Objects.nonNull(request.getName())){
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + request.getName() + "%"));
            }
            if(Objects.nonNull(request.getCategory())){
                predicates.add(criteriaBuilder.equal(root.join("category").get("name"), request.getCategory()));
            }
            return query.where(predicates.toArray(predicates.toArray(new Predicate[0]))).getRestriction();
        };
        if(request.getSort() == Sort.Direction.ASC){
            sort1 = Sort.Direction.ASC;
        }else if(request.getSort() == Sort.Direction.DESC){
            sort1 = Sort.Direction.DESC;
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid value for soring");
        }
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort1, request.getColumn());
        Page<Product> products = productRepository.findAll(productSpecification, pageable);
        List<ProductResponse> productResponses = products.getContent().stream().map(product -> ProductResponse.builder()
                .createdAt(product.getCreatedAt())
                .name(product.getName())
                .category(product.getCategory())
                .details(product.getDetails())
                .id(product.getId())
                .image1(product.getImage1())
                .image2(product.getImage2())
                .image3(product.getImage3())
                .price(product.getPrice())
                .rate(product.getRate())
                .title(product.getTitle())
                .orderItems(product.getOrderItems())
                .quantity(product.getQuantity())
                .updatedAt(product.getUpdatedAt())
                .user(product.getUser())
                .wishLists(product.getWishLists())
                .createdAt(product.getCreatedAt())
                .build()).collect(Collectors.toList());
        boolean ascending = request.getSort().isAscending();
        PropertyComparator.sort(productResponses, new MutableSortDefinition(request.getColumn(), true, ascending));
        return new PageImpl<>(productResponses, pageable, products.getTotalElements());
    }

    @Override
    public ProductResponse create(ProductRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        Category category = categoryRepository.findByName(request.getCategory()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "category not found"));
        Product product = new Product();
        product.setName(request.getName());
        product.setQuantity(request.getQuantity());
        product.setCategory(category);
        product.setPrice(request.getPrice());
        if(Objects.nonNull(request.getImage1())){
            String originalFilename1 = request.getImage1().getOriginalFilename();
            String fileExtension = originalFilename1.substring(originalFilename1.lastIndexOf("."));
            String uniqueFileName = System.currentTimeMillis() + fileExtension;
            String imagePath = FOLDER_PATH + File.separator + uniqueFileName;
            product.setImage1(uniqueFileName);
            try {
                request.getImage1().transferTo(new File(imagePath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            product.setImage1(null);
        }
        if(Objects.nonNull(request.getImage2())){
            String originalFilename2 = request.getImage2().getOriginalFilename();
            String fileExtension = originalFilename2.substring(originalFilename2.lastIndexOf("."));
            String uniqueFileName = System.currentTimeMillis() + fileExtension;
            String imagePath = FOLDER_PATH + File.separator + uniqueFileName;
            product.setImage2(uniqueFileName);
            try {
                request.getImage2().transferTo(new File(imagePath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            };
        }else{
            product.setImage2(null);
        }
        if(Objects.nonNull(request.getImage3())){
            String originalFilename3 = request.getImage3().getOriginalFilename();
            String fileExtension = originalFilename3.substring(originalFilename3.lastIndexOf("."));
            String uniqueFileName = System.currentTimeMillis() + fileExtension;
            String imagePath = FOLDER_PATH + File.separator + uniqueFileName;
            product.setImage3(uniqueFileName);
            try {
                request.getImage3().transferTo(new File(imagePath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            product.setImage3(null);
        }
        product.setDetails(request.getDetails());
        product.setRate(request.getRate());
        product.setUser(user);
        productRepository.save(product);
        return ProductResponse.builder()
                .createdAt(product.getCreatedAt())
                .name(product.getName())
                .category(product.getCategory())
                .details(product.getDetails())
                .id(product.getId())
                .image1(product.getImage1())
                .image2(product.getImage2())
                .image3(product.getImage3())
                .price(product.getPrice())
                .rate(product.getRate())
                .title(product.getTitle())
                .orderItems(product.getOrderItems())
                .quantity(product.getQuantity())
                .updatedAt(product.getUpdatedAt())
                .user(product.getUser())
                .wishLists(product.getWishLists())
                .createdAt(product.getCreatedAt())
                .build();
    }

    @Override
    public UpdateProductResponse updateProduct(Long id, ProductRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Product product = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));
        Category category = categoryRepository.findByName(request.getCategory()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "category not found"));
        if(!email.equals(product.getUser().getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "you are not allowed to do this action");
        }
        if(Objects.nonNull(request.getName())){
            product.setName(request.getName());
        }
        if(Objects.nonNull(request.getQuantity())){
            product.setQuantity(request.getQuantity());
        }
        if(Objects.nonNull(request.getCategory())){
            product.setCategory(category);
        }
        if(Objects.nonNull(request.getPrice())){
            product.setPrice(request.getPrice());
        }
        if(Objects.nonNull(request.getImage1())) {
            if (request.getImage1().getOriginalFilename().length() == 0) {
                File existingImage = new File(FOLDER_PATH + File.separator + product.getImage1());
                if (existingImage.exists()) {
                    existingImage.delete();
                } else {
                    System.out.println("File not found. Deletion skipped1.");
                }
                product.setImage1(null);
            } else if(request.getImage1().getOriginalFilename().length() > 0) {
                File existingImage = new File(FOLDER_PATH + File.separator + product.getImage1());
                if (existingImage.exists()) {
                    existingImage.delete();
                } else {
                    System.out.println("File not found. Deletion skipped1.");
                }
                String originalFilename1 = request.getImage1().getOriginalFilename();
                String fileExtension = originalFilename1.substring(originalFilename1.lastIndexOf("."));
                String uniqueFileName = System.currentTimeMillis() + fileExtension;
                String imagePath = FOLDER_PATH + File.separator + uniqueFileName;
                product.setImage1(uniqueFileName);
                try {
                    System.out.println("test1");
                    request.getImage1().transferTo(new File(imagePath));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                };
            }
        }

        if(Objects.nonNull(request.getImage2())) {
            System.out.println("\n\n\n\ntest21");
            if (request.getImage2().getOriginalFilename().length() == 0) {
                System.out.println("" + "test2");
                File existingImage = new File(FOLDER_PATH + File.separator + product.getImage2());
                if (existingImage.exists()) {
                    existingImage.delete();
                } else {
                    System.out.println("File not found. Deletion skipped2.");
                }
                product.setImage2(null);
            } else if(request.getImage2().getOriginalFilename().length() == 0) {
                File existingImage = new File(FOLDER_PATH + File.separator + product.getImage2());
                if (existingImage.exists()) {
                    existingImage.delete();
                } else {
                    System.out.println("File not found. Deletion skipped2.");
                }
                String originalFilename2 = request.getImage2().getOriginalFilename();
                String fileExtension = originalFilename2.substring(originalFilename2.lastIndexOf("."));
                String uniqueFileName = System.currentTimeMillis() + fileExtension;
                String imagePath = FOLDER_PATH + File.separator + uniqueFileName;
                product.setImage2(uniqueFileName);
                try {
                    request.getImage2().transferTo(new File(imagePath));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                };
            }else{
                File existingImage = new File(FOLDER_PATH + File.separator + product.getImage2());
                if (existingImage.exists()) {
                    existingImage.delete();
                } else {
                    System.out.println("File not found. Deletion skipped2.");
                }
                String originalFilename2 = request.getImage2().getOriginalFilename();
                String fileExtension = originalFilename2.substring(originalFilename2.lastIndexOf("."));
                String uniqueFileName = System.currentTimeMillis() + fileExtension;
                String imagePath = FOLDER_PATH + File.separator + uniqueFileName;
                product.setImage2(uniqueFileName);
                try {
                    request.getImage2().transferTo(new File(imagePath));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                };
            }
        }

        if(Objects.nonNull(request.getImage3())) {
            if (request.getImage3().getOriginalFilename().length() == 0) {
                File existingImage = new File(FOLDER_PATH + File.separator + product.getImage3());
                if (existingImage.exists()) {
                    existingImage.delete();
                } else {
                    System.out.println("\n\n\n\nFile not found. Deletion skipped3.");
                }
                product.setImage3(null);
            } else if(request.getImage3().getOriginalFilename().length() == 0){
                File existingImage = new File(FOLDER_PATH + File.separator + product.getImage3());
                if (existingImage.exists()) {
                    existingImage.delete();
                } else {
                    System.out.println("\n\n\n\nFile not found. Deletion skipped3.");
                }
                String originalFilename3 = request.getImage3().getOriginalFilename();
                String fileExtension = originalFilename3.substring(originalFilename3.lastIndexOf("."));
                String uniqueFileName = System.currentTimeMillis() + fileExtension;
                String imagePath = FOLDER_PATH + File.separator + uniqueFileName;
                product.setImage3(uniqueFileName);
                try {
                    request.getImage3().transferTo(new File(imagePath));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                };
            }else{
                File existingImage = new File(FOLDER_PATH + File.separator + product.getImage3());
                if (existingImage.exists()) {
                    existingImage.delete();
                } else {
                    System.out.println("\n\n\n\nFile not found. Deletion skipped3.");
                }
                String originalFilename3 = request.getImage3().getOriginalFilename();
                String fileExtension = originalFilename3.substring(originalFilename3.lastIndexOf("."));
                String uniqueFileName = System.currentTimeMillis() + fileExtension;
                String imagePath = FOLDER_PATH + File.separator + uniqueFileName;
                product.setImage3(uniqueFileName);
                try {
                    request.getImage3().transferTo(new File(imagePath));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                };
            }
        }

        if(Objects.nonNull(request.getDetails())){
            product.setDetails(request.getDetails());
        }
        if(Objects.nonNull(request.getRate())){
            product.setRate(request.getRate());
        }
        product.setUpdatedAt(new Date(System.currentTimeMillis()));
        productRepository.save(product);
        return UpdateProductResponse.builder()
                .createdAt(product.getCreatedAt())
                .name(product.getName())
                .category(product.getCategory())
                .details(product.getDetails())
                .id(product.getId())
                .image1(product.getImage1())
                .image2(product.getImage2())
                .image3(product.getImage3())
                .price(product.getPrice())
                .rate(product.getRate())
                .title(product.getTitle())
                .quantity(product.getQuantity())
                .updatedAt(product.getUpdatedAt())
                .createdAt(product.getCreatedAt())
                .build();
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));
        return ProductResponse.builder()
                .createdAt(product.getCreatedAt())
                .name(product.getName())
                .category(product.getCategory())
                .details(product.getDetails())
                .id(product.getId())
                .image1(product.getImage1())
                .image2(product.getImage2())
                .image3(product.getImage3())
                .price(product.getPrice())
                .user(product.getUser())
                .rate(product.getRate())
                .title(product.getTitle())
                .quantity(product.getQuantity())
                .updatedAt(product.getUpdatedAt())
                .createdAt(product.getCreatedAt())
                .build();
    }

    @Override
    public void deleteProduct(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        Product product = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));
        if(user.getRole() == Role.SELLER && email.equals(product.getUser().getEmail())){
            productRepository.delete(product);
            return;
        }if(user.getRole() == Role.ADMIN){
            productRepository.delete(product);
            return;
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "you can't do this action");
        }
    }
}
