package com.spring.asset_craft.service;

import com.spring.asset_craft.entity.ProductImage;
import com.spring.asset_craft.entity.ProductUser;
import com.spring.asset_craft.entity.Review;
import com.spring.asset_craft.repository.ProductImageRepository;
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

import java.nio.file.Path;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static com.spring.asset_craft.entity.ProductUser.ProductUserStatus.*;


@Service
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private ProductUserRepository productUserRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private AppDAO appDAO;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductUserRepository productUserRepository, ReviewRepository reviewRepository,
                          UserService userService, ProductImageRepository productImageRepository, AppDAO appDAO) {
        this.productRepository = productRepository;
        this.productUserRepository = productUserRepository;
        this.reviewRepository = reviewRepository;
        this.userService = userService;
        this.productImageRepository = productImageRepository;
        this.appDAO = appDAO;
    }

    public String getOwnerUsername(Long productId) {
        return productUserRepository.getOwnerUsername(productId)
                .orElse(null);
    }

    public Long getOwnerId(Long productId) {
        return productUserRepository.getOwnerId(productId);
    }

    private ProductDTO convertToSmallProductDTO(Product product) {

        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                getProductRating(product.getId()),
                product.getCategory(),
                product.getProductImages(),
                getOwnerUsername(product.getId()),
                false,
                false,
                false
        );
    }

    public List<ProductDTO> populateSmallProductDTOS(List<Product> products) {
        return products.stream()
                .map(this::convertToSmallProductDTO)
                .collect(Collectors.toList());
    }

    public Product getProductById(Long id){
        return productRepository.findProductById(id)
                .orElse(null);
    }

    public ProductDTO getBigProductDTO(Long productId){

        Product product = getProductById(productId);

        return new ProductDTO(
                productId,
                product.getName(),
                product.getPrice(),
                getProductRating(productId),
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

    public List<ReviewDTO> getReviewsDTO(Long productId){
        return productRepository.findProductReviews(productId);
    }

    public List<ProductDTO> searchProducts(String name, Float minPrice, Float maxPrice, Double rating, List<String> categories) {
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

        if(!isInCart(productUser) && !isOwner(productUser) && !isBought(productUser))
            productUserRepository.save(productUser);
    }
    public boolean isInCart(ProductUser productUser){
        Long productId = productUser.getProduct().getId();
        Long userId = productUser.getUser().getId();
        return productUserRepository.inCart(productId, userId);
    }
    public boolean isOwner(ProductUser productUser){
        Long userId = productUser.getUser().getId();
        Long ownerId = getOwnerId(productUser.getProduct().getId());
        return ownerId.equals(userId);
    }
    public boolean isBought(ProductUser productUser){
        Long userId = productUser.getUser().getId();
        Long productId = productUser.getProduct().getId();
        return productUserRepository.inBought(productId,userId);
    }

    public void deleteFromCart(Long productId, String username){
        productUserRepository.findProductInCartByProductId(productId, username)
                .ifPresent(productUser -> productUserRepository.delete(productUser));
    }

    public void addReview(Long productId, String review, Float rating, String username) {

        reviewRepository.save(new Review(review, rating, getProductById(productId), userService.getUserByUsername(username)));
    }

    public Double getProductRating(Long productId) {
        return reviewRepository.findProductRatingById(productId);
    }

    public void addProduct(List<String> paths, String name, String category, float price, String description, Principal principal) {
        Product product = new Product(
                name,
                price,
                category,
                description
        );
        Product savedProduct = productRepository.save(product);

        for (String path : paths) {
            ProductImage productImage = new ProductImage(
                    path,
                    savedProduct
            );
            productImageRepository.save(productImage);
        }

        ProductUser productUser = new ProductUser(
                savedProduct,
                userService.getUserByUsername(principal.getName()),
                OWNER
        );
        productUserRepository.save(productUser);
    }
}
