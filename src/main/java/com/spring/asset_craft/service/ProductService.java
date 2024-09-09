package com.spring.asset_craft.service;

import com.spring.asset_craft.dto.FormProductDTO;
import com.spring.asset_craft.dto.ProductDTO;
import com.spring.asset_craft.dto.ReviewDTO;
import com.spring.asset_craft.entity.Product;
import com.spring.asset_craft.entity.ProductImage;
import com.spring.asset_craft.entity.ProductUser;

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

    List<ProductDTO> getSoldProducts(String username);

    List<ProductDTO> getBoughtProducts(String username);

    List<ProductDTO> getProductsInCart(String username);

    void addToCart(ProductUser productUser);

    boolean isInCart(ProductUser productUser);

    boolean isOwner(ProductUser productUser);

    boolean isBought(ProductUser productUser);

    void deleteFromCart(Long productId, String username);

    void addReview(Long productId, String review, Float rating, String username);

    Double getProductRating(Long productId);

    void addProduct(List<String> paths, String name, String category, float price, String description, Principal principal);

    void userBought(List<Long> productsIds);

    List<ProductImage> getImagesPath(Long productId);


    void updateProduct(FormProductDTO productForm);

    FormProductDTO getFormProductById(Long id);
}
