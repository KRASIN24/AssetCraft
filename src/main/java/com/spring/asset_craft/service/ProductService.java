package com.spring.asset_craft.service;

import com.spring.asset_craft.search.ProductRepository;
import com.spring.asset_craft.search.ProductSpecification;
import com.spring.asset_craft.dao.AppDAO;
import com.spring.asset_craft.dto.BigProductDTO;
import com.spring.asset_craft.dto.ReviewDTO;
import com.spring.asset_craft.dto.SmallProductDTO;
import com.spring.asset_craft.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private AppDAO appDAO;

    @Autowired
    public ProductService(ProductRepository productRepository, AppDAO appDAO) {
        this.productRepository = productRepository;
        this.appDAO = appDAO;
    }

    public BigProductDTO getBigProductDTO(int id){

        Product product = appDAO.findProductById(id);
        String owner = appDAO.getOwnerUsername(id);
        List<ReviewDTO> reviews= appDAO.getProductReviews(id);

        BigProductDTO bigProductDTO = new BigProductDTO();
        bigProductDTO.setName(product.getName());
        bigProductDTO.setProductImages(product.getProductImages());
        bigProductDTO.setDescription(product.getDescription());
        bigProductDTO.setId(product.getId());
        bigProductDTO.setPrice(product.getPrice());
        bigProductDTO.setRating(product.getRating());
        bigProductDTO.setCategory(product.getCategory());
        bigProductDTO.setOwner(owner);
        bigProductDTO.setReviews(reviews);
        return bigProductDTO;
    }

    public SmallProductDTO getSmallProductDTO(int id){
        Product product = appDAO.findProductById(id);
        String owner = appDAO.getOwnerUsername(id);
        SmallProductDTO smallProductDTO = new SmallProductDTO();
        smallProductDTO.setId(product.getId());
        smallProductDTO.setName(product.getName());
        smallProductDTO.setPrice(product.getPrice());
        smallProductDTO.setRating(product.getRating());
        smallProductDTO.setImgPath(product.getProductImages());
        smallProductDTO.setOwner(owner);
        return smallProductDTO;
    }

    public List<Product> searchProducts(String name, String category, Float minPrice, Float maxPrice) {
        Specification<Product> spec = Specification.where(null);  // Start with an empty specification

        if (name != null && !name.isEmpty()) {
            spec = spec.and(ProductSpecification.hasName(name));  // Add name filter
        }
        if (category != null && !category.isEmpty()) {
            spec = spec.and(ProductSpecification.hasCategory(category));  // Add category filter
        }
        if (minPrice != null && maxPrice != null) {
            spec = spec.and(ProductSpecification.hasPriceBetween(minPrice, maxPrice));  // Add price range filter
        }

        return productRepository.findAll(spec);
    }

    public List<SmallProductDTO> populateSmallProductDTOS(List<Product> products) {
        return products.stream()
                .map(this::convertToSmallProductDTO)
                .collect(Collectors.toList());
    }

    private SmallProductDTO convertToSmallProductDTO(Product product) {
        // Fetch the owner username based on the product ID
        String ownerUsername = appDAO.getOwnerUsername(product.getId());

        // Create and return a SmallProductDTO object
        return new SmallProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getRating(),
                product.getProductImages(),
                ownerUsername
        );
    }
}
