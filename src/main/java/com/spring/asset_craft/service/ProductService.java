package com.spring.asset_craft.service;

import com.spring.asset_craft.entity.ProductUser;
import com.spring.asset_craft.entity.Review;
import com.spring.asset_craft.repository.ProductUserRepository;
import com.spring.asset_craft.repository.ProductRepository;
import com.spring.asset_craft.repository.ReviewRepository;
import com.spring.asset_craft.search.ProductSpecification;
import com.spring.asset_craft.dao.AppDAO;
import com.spring.asset_craft.dto.ReviewDTO;
import com.spring.asset_craft.dto.ProductDTO;
import com.spring.asset_craft.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private ProductUserRepository productUserRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private AppDAO appDAO;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductUserRepository productUserRepository, ReviewRepository reviewRepository, UserService userService, AppDAO appDAO) {
        this.productRepository = productRepository;
        this.productUserRepository = productUserRepository;
        this.reviewRepository = reviewRepository;
        this.userService = userService;
        this.appDAO = appDAO;
    }

    public String getOwnerUsername(int productId) {
        return productUserRepository.getOwnerUsername(productId)
                .orElse(null);
    }

    private ProductDTO convertToSmallProductDTO(Product product) {

        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getRating(),
                product.getCategory(),
                product.getProductImages(),
                getOwnerUsername(product.getId())
        );
    }

    public List<ProductDTO> populateSmallProductDTOS(List<Product> products) {
        return products.stream()
                .map(this::convertToSmallProductDTO)
                .collect(Collectors.toList());
    }

    public Product getProductById(int id){
        return productRepository.findProductById(id)
                .orElse(null);
    }

    public ProductDTO getBigProductDTO(int productId){

        Product product = getProductById(productId);

        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getRating(),
                getOwnerUsername(productId),
                product.getDescription(),
                product.getCategory(),
                getReviewsDTO(productId),
                product.getProductImages()
        );
    }

    public List<ProductDTO> getAllSmallProductsDTO(){
        return populateSmallProductDTOS(productRepository.findAll());
    }

    public List<ReviewDTO> getReviewsDTO(int productId){
        return productRepository.findProductReviews(productId);
    }

    public List<ProductDTO> searchProducts(String name, Float minPrice, Float maxPrice, Float rating, List<String> categories) {
        Specification<Product> spec = Specification.where(null);  // Start with an empty specification

        if (name != null && !name.isEmpty()) {
            spec = spec.and(ProductSpecification.hasName(name));  // Add name filter
        }
        if (minPrice != null || maxPrice != null) {
            spec = spec.and(ProductSpecification.hasPriceBetween(minPrice, maxPrice));  // Add price range filter
        }
        if (rating != null) {
            spec = spec.and(ProductSpecification.hasRating(rating));  // Add name filter
        }
        if (categories != null && !categories.isEmpty()) {
            spec = spec.and(ProductSpecification.hasCategory(categories));  // Add category filter
        }

        return populateSmallProductDTOS(productRepository.findAll(spec));
    }

    public List<ProductDTO> searchProducts(String name, Float rating, List<String> categories) {
        Specification<Product> spec = Specification.where(null);  // Start with an empty specification

        if (name != null && !name.isEmpty()) {
            spec = spec.and(ProductSpecification.hasName(name));  // Add name filter
        }
        if (rating != null) {
            spec = spec.and(ProductSpecification.hasRating(rating));  // Add name filter
        }
        if (categories != null && !categories.isEmpty()) {
            spec = spec.and(ProductSpecification.hasCategory(categories));  // Add category filter
        }

        return populateSmallProductDTOS(productRepository.findAll(spec));
    }

    public float biggestPrice(List<ProductDTO> productDTOs) {

        return productDTOs.stream()
                .map(ProductDTO::getPrice)
                .max(Float::compare)
                .orElse(0.0f);
    }

    public float smallestPrice(List<ProductDTO> ProductDTOs) {
        return ProductDTOs.stream()
                .map(ProductDTO::getPrice)
                .min(Float::compare)
                .orElse(0.0f);
    }

    public List<ProductDTO> getSoldProducts(String username){
        List<Product> products = productUserRepository.findProductsSoldByUser(username);
        return populateSmallProductDTOS(products);
    }

    public List<ProductDTO> getBoughtProducts(String username){
        List<Product> products = productUserRepository.findProductsBoughtByUser(username);
        return populateSmallProductDTOS(products);
    }


    public List<ProductDTO> getProductsInCart(String username){
        List<Product> products = productUserRepository.findProductsInCartByUser(username);
        return populateSmallProductDTOS(products);
    }


    public void addToCart(ProductUser productUser) {

        if(!isInCart(productUser.getProduct().getId(), productUser.getUser().getId()))
            productUserRepository.save(productUser);
    }
    public boolean isInCart(int productId, int userId){
        return productUserRepository.inCart(productId, userId);
    }

    public void deleteFromCart(int productId, String username){
        productUserRepository.findProductInCartByProductId(productId, username)
                .ifPresent(productUser -> productUserRepository.delete(productUser));
    }

    public void addReview(int productId, String review, Float rating, String username) {

        reviewRepository.save(new Review(review, rating, getProductById(productId), userService.getUserByUsername(username)));
    }
}
