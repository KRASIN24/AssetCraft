package com.spring.asset_craft.search;

import com.spring.asset_craft.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ProductSpecification {
    public static Specification<Product> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                name == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Product> hasRating(Float rating) {
        return (root, query, criteriaBuilder) ->
                rating == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), rating);
    }

    public static Specification<Product> hasCategory(List<String> categories) {
        return (root, query, criteriaBuilder) ->
                categories == null || categories.isEmpty() ? null : root.get("category").in(categories);
    }


    public static Specification<Product> hasPriceBetween(Float minPrice, Float maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if (minPrice != null && maxPrice != null) {
                return criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
            } else if (minPrice != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
            } else if (maxPrice != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
            } else {
                return null;
            }
        };
    }
}
