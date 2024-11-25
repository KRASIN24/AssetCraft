package com.spring.asset_craft.controller;

import com.spring.asset_craft.dao.AppDAO;
import com.spring.asset_craft.dto.FormProductDTO;
import com.spring.asset_craft.dto.ProductDTO;
import com.spring.asset_craft.entity.ProductUser;
import com.spring.asset_craft.entity.User;
import com.spring.asset_craft.repository.ProductRepository;
import com.spring.asset_craft.repository.ProductUserRepository;
import com.spring.asset_craft.repository.UserRepository;
import com.spring.asset_craft.security.WebUser;
import com.spring.asset_craft.service.impl.ProductServiceImpl;
import com.spring.asset_craft.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.spring.asset_craft.entity.ProductUser.ProductUserStatus.*;

@org.springframework.stereotype.Controller
public class Controller {

    private ProductServiceImpl productService;
    private UserService userService;
    private AppDAO appDAO;

    @Autowired
    public Controller(ProductServiceImpl productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }


    //TODO: Make it better
    @GetMapping("/")
    public String showIndex(Model model,Principal principal){

        List<ProductDTO> ProductDTOs = productService.getAllSmallProductsDTO();

        if (principal != null) {
            String username = principal.getName();
            Long userId = userService.getUserByUsername(username).getId();
            for (ProductDTO product : ProductDTOs) {
                product.setOwner(productService.hasStatus(product.getId(),userId,OWNER));
                product.setInCart(productService.hasStatus(product.getId(),userId,CART));
                product.setBought(productService.hasStatus(product.getId(),userId,BUYER));
            }
        }

        model.addAttribute("products", ProductDTOs);

        return "index";
    }


    @GetMapping("/shop")
    public String showShop(@RequestParam(required = false) String name,
                           @RequestParam(required = false) Float minPrice,
                           @RequestParam(required = false) Float maxPrice,
                           @RequestParam(required = false) Double rating,
                           @RequestParam(required = false) List<String> categories,
                           Principal principal,
                           Model model){

        List<ProductDTO> ProductDTOs = productService.searchProducts(name, minPrice, maxPrice, rating, categories);

        if (principal != null) {
            String username = principal.getName();
            Long userId = userService.getUserByUsername(username).getId();
            for (ProductDTO product : ProductDTOs) {
                product.setOwner(productService.hasStatus(product.getId(),userId,OWNER));
                product.setInCart(productService.hasStatus(product.getId(),userId,CART));
                product.setBought(productService.hasStatus(product.getId(),userId,BUYER));
            }
        }

        for (ProductDTO product : ProductDTOs) {
            product.setWhishlistAmout(Random.from(new Random()).nextInt(100));
        }

        model.addAttribute("products", ProductDTOs);

        List<ProductDTO> priceProductDTOs = productService.getAllSmallProductsDTO();
        float biggestPrice = productService.biggestPrice(priceProductDTOs);
        model.addAttribute("biggestPrice", biggestPrice);

        float smallestPrice = productService.smallestPrice(priceProductDTOs);
        model.addAttribute("smallestPrice", smallestPrice);


        if (minPrice == null && maxPrice == null){
            maxPrice = biggestPrice;
            minPrice = smallestPrice;
        }

        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("minPrice", minPrice);

        if (categories != null)
            model.addAttribute("categories", categories);

        model.addAttribute("rating", rating);

        model.addAttribute("name", name);

        return "shop/shop";
    }

    @GetMapping("/contact")
    public String showContact(){
        return "contact";
    }


}
