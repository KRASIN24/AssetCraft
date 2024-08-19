package com.spring.asset_craft.controller;

import com.spring.asset_craft.dao.AppDAO;
import com.spring.asset_craft.dto.BigProductDTO;
import com.spring.asset_craft.dto.SmallProductDTO;
import com.spring.asset_craft.entity.Product;
import com.spring.asset_craft.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private ProductService productService;
    private AppDAO appDAO;

    public Controller(AppDAO appDAO) {
        this.appDAO = appDAO;
    }

    @GetMapping("/")
    public String showIndex(Model model){
        String name = "";
        List<Product> products = appDAO.findProductByName(name);
        List<SmallProductDTO> smallProductDTOS = new ArrayList<>();
        //model.addAttribute("products", products);

        for (Product product : products) {
            // Fetch the owner username based on the product ID
            String ownerUsername = appDAO.getOwnerUsername(product.getId());

            // Create a new MinProduct object with the owner username
            SmallProductDTO smallProductDTO = new SmallProductDTO(product.getId(), product.getName(), product.getPrice(), product.getRating(), product.getProductImages(), ownerUsername);

            // Add the MinProduct object to the list
            smallProductDTOS.add(smallProductDTO);
        }

        // Add the map to the model
        model.addAttribute("products", smallProductDTOS);
        System.out.println((smallProductDTOS.get(0)).toString());
        return "index";
    }


    @GetMapping("/shop")
    public String showShop(){
        return "shop";
    }


    @GetMapping("/account")
    public String showAccount(){
        return "account";
    }

    @GetMapping("/cart")
    public String showCart(){
        return "cart";
    }

    @GetMapping("/contact")
    public String showContact(){
        return "contact";
    }

    // TODO: Add product page
    @GetMapping("/productPage/{id}")
    public String showProductPage(@PathVariable("id") int id, Model model) {
        //Product product = appDAO.findProductById(id);
        BigProductDTO bigProductDTO = productService.getBigProductDTO(id);
        model.addAttribute("product", bigProductDTO);
        return "productPage";
    }

    // -------------------------------------

    @GetMapping("/admin")
    public String showAdmin(){
        return "admin";
    }


    @GetMapping("/login")
    public String showLogin(){
        return "login";
    }

    // TODO: Add custom logging page
    // TODO: Move logging page from start to account
}
