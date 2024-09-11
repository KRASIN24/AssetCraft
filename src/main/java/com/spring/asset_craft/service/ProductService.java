package com.spring.asset_craft.service;

import com.spring.asset_craft.dto.FormProductDTO;
import com.spring.asset_craft.dto.ProductDTO;
import com.spring.asset_craft.dto.ReviewDTO;
import com.spring.asset_craft.entity.Product;
import com.spring.asset_craft.entity.ProductImage;
import com.spring.asset_craft.entity.ProductUser.ProductUserStatus;

import java.security.Principal;
import java.util.List;

public interface ProductService {

    String getOwnerUsername(Long productId);

    Long getOwnerId(Long productId);

    ProductDTO convertToSmallProductDTO(Product product);

    List<ProductDTO> populateSmallProductDTOS(List<Product> products);

    Product getProductById(Long id);

    ProductDTO getBigProductDTO(Long productId);

    List<ProductDTO> getAllSmallProductsDTO();

    List<ReviewDTO> getReviewsDTO(Long productId);

    List<ProductDTO> searchProducts(String name, Float minPrice, Float maxPrice, Double rating, List<String> categories);

    float biggestPrice(List<ProductDTO> productDTOs);

    float smallestPrice(List<ProductDTO> ProductDTOs);

    List<ProductDTO> getProductsWithStatus(String username, ProductUserStatus status);

    //Cart

    void deleteFromCart(Long productId, String username);

    void userBought(List<Long> productsIds);

    void addToCart(Long productId, Long userId);
//

    void addReview(Long productId, String review, Float rating, String username);

    Double getProductRating(Long productId);

    void addProduct(List<String> paths, String name, String category, float price, String description, Principal principal);



    List<ProductImage> getImages(Long productId);


    void updateProduct(Long productId, FormProductDTO productForm, List<String> paths);

    FormProductDTO getFormProductById(Long id);

    void deleteImage(Long imageId);



    boolean hasStatus(Long productId, Long userId, ProductUserStatus productUserStatus);
}
