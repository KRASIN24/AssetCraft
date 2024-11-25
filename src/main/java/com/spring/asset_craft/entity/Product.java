package com.spring.asset_craft.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private float price;
    @Column(name = "category")
    private String category;
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> productImages;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<ProductUser> productUsers;

    public Product() {
    }

    public Product(Long id, String name, float price, String category, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
    }

    public Product(String name, float price, String category, String description) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }

    public void addReview(Review review) {
        if (reviews == null) {
            reviews = new ArrayList<>();
        }
        reviews.add(review);

        review.setProduct(this);
    }

    public void addProductImage(ProductImage productImage){
        if (productImages == null) {
            productImages = new ArrayList<>();
        }
        productImages.add(productImage);

        productImage.setProduct(this);
    }

    public List<ProductUser> getAssociationProductUsers() {
        return productUsers;
    }

    public void setAssociationProductUsers(List<ProductUser> productUsers) {
        this.productUsers = productUsers;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
