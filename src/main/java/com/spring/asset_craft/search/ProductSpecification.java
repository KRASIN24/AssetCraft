package com.spring.asset_craft.search;

import com.spring.asset_craft.entity.Product;
import com.spring.asset_craft.entity.Review;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ProductSpecification {
    public static Specification<Product> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                name == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Product> hasRating(Double minRating) {
        return (root, query, criteriaBuilder) -> {
            if (minRating == null) {
                return null;
            }

            Subquery<Double> subquery = query.subquery(Double.class);
            Root<Review> reviewRoot = subquery.from(Review.class);

            subquery.select(criteriaBuilder.avg(reviewRoot.get("rating")))
                    .where(criteriaBuilder.equal(reviewRoot.get("product").get("id"), root.get("id")));

            return criteriaBuilder.greaterThanOrEqualTo(subquery, minRating);
        };
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
