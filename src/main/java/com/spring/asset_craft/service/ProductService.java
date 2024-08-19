package com.spring.asset_craft.service;

import com.spring.asset_craft.dao.AppDAO;
import com.spring.asset_craft.dto.BigProductDTO;
import com.spring.asset_craft.dto.ReviewDTO;
import com.spring.asset_craft.entity.Product;
import com.spring.asset_craft.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private AppDAO appDAO;
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

}
