package com.spring.asset_craft.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product_user")
public class ProductUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProductUserStatus status;

    public ProductUser() {
    }

    public ProductUser(Long id, Product product, User user, ProductUserStatus status) {
        this.id = id;
        this.product = product;
        this.user = user;
        this.status = status;
    }

    public ProductUser(Product product, User user, ProductUserStatus status) {
        this.product = product;
        this.user = user;
        this.status = status;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ProductUserStatus getStatus() {
        return status;
    }

    public void setStatus(ProductUserStatus status) {
        this.status = status;
    }

    public enum ProductUserStatus {
        OWNER,
        BUYER,
        WISHLIST,
        CART
    }
}
