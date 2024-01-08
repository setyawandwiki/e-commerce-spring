package com.setyawandwiki.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "name")
    private String name;
    @Column(name = "details")
    private String details;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "price")
    private Double price;
    @Column(name = "rate")
    private Double rate;
//    @Lob //  convert file to byte array. jika ingin store binary format ke db
    @Column(name = "image1")
    private String image1;
//    private Byte[] image1;
//    @Lob
    @Column(name = "image2")
//    private Byte[] image2;
    private String image2;
//    @Lob
    @Column(name = "image3")
//    private Byte[] image3;
    private String image3;
    private String type;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<OrderItem> orderItems;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    @JsonManagedReference
    private Category category;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<WishList> wishLists;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;
    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}

