package com.spring.asset_craft.service;

import com.spring.asset_craft.entity.ProductUser;
import com.spring.asset_craft.repository.ProductUserRepository;
import com.spring.asset_craft.repository.ProductRepository;
import com.spring.asset_craft.search.ProductSpecification;
import com.spring.asset_craft.dao.AppDAO;
import com.spring.asset_craft.dto.ReviewDTO;
import com.spring.asset_craft.dto.ProductDTO;
import com.spring.asset_craft.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private ProductUserRepository productUserRepository;
    @Autowired
    private AppDAO appDAO;

    @Autowired
    public ProductService(ProductRepository productRepository, AppDAO appDAO) {
        this.productRepository = productRepository;
        this.appDAO = appDAO;
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

    public ProductDTO getSmallProductDTO(int id){
        Product product = getProductById(id);

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

    public List<Product> searchProducts(String name, Float minPrice, Float maxPrice, Float rating, List<String> categories) {
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

        return productRepository.findAll(spec);
    }

    public List<ProductDTO> populateSmallProductDTOS(List<Product> products) {
        return products.stream()
                .map(this::convertToSmallProductDTO)
                .collect(Collectors.toList());
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

    public float biggestPrice(List<ProductDTO> ProductDTOS) {

        return ProductDTOS.stream()
                .map(ProductDTO::getPrice)
                .max(Float::compare)
                .orElse(0.0f);
    }

    public float smallestPrice(List<ProductDTO> ProductDTOS) {
        return ProductDTOS.stream()
                .map(ProductDTO::getPrice)
                .min(Float::compare)
                .orElse(0.0f);
    }


    public Product getProductById(int id){
        return productRepository.findProductById(id)
                .orElse(null);
    }

    public List<Product> getProductsByName(String name){
        return productRepository.findProductByName(name);
    }

    public List<ProductDTO> getProductsInCart(String username){
        List<ProductDTO> productDTOs = new ArrayList<>();
        List<Product> products = productUserRepository.findCartProductsByUser(username);

        for (Product product : products) {
            ProductDTO dto = new ProductDTO(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getRating(),
                    product.getCategory(),
                    product.getProductImages(),
                    getOwnerUsername(product.getId())
            );
            productDTOs.add(dto);
        }
        return productDTOs;

    }

        public ProductUser getProductInCartToDelete(int productId, String username){
            return productUserRepository.findCartProductByProductId(productId, username)
                    .orElse(null);
        }

        public String getOwnerUsername(int productId) {
            return productUserRepository.getOwnerUsername(productId)
                    .orElse(null);
        }

        public List<ReviewDTO> getReviewsDTO(int productId){
            return productRepository.findProductReviews(productId);
        }

        public boolean isInCart(int productId, int userId){
            return productUserRepository.inCart(productId, userId);
        }

        public List<Product> getAllProducts(){
            return productRepository.findAll();
        }
}
