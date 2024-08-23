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
import org.springframework.web.bind.annotation.RequestParam;

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

    //TODO: Make it better
    @GetMapping("/")
    public String showIndex(Model model){
        String name = "";
        List<Product> products = appDAO.findProductByName(name);
        List<SmallProductDTO> smallProductDTOS = productService.populateSmallProductDTOS(products);
        // Add the map to the model
        model.addAttribute("products", smallProductDTOS);
        return "index";
    }


    @GetMapping("/shop")
    public String showShop(@RequestParam(required = false) String name,
                           @RequestParam(required = false) String category,
                           @RequestParam(required = false) Float minPrice,
                           @RequestParam(required = false) Float maxPrice,
                           Model model){

        List<Product> products = productService.searchProducts(name, category, minPrice, maxPrice);

        List<SmallProductDTO> smallProductDTOS = productService.populateSmallProductDTOS(products);
        model.addAttribute("products", smallProductDTOS);

        float biggestPrice = productService.biggestPrice(smallProductDTOS);
        model.addAttribute("biggestPrice", biggestPrice);

        float smallestPrice = productService.smallestPrice(smallProductDTOS);
        model.addAttribute("smallestPrice", smallestPrice);

        return "shop";
    }

    @GetMapping("/signInPage")
    public String showSignIn(){  return "signInPage";
    }

    @GetMapping("/account")
    public String showAccount(){  return "account";
    }

    @GetMapping("/cart")
    public String showCart(){
        return "cart";
    }

    @GetMapping("/contact")
    public String showContact(){
        return "contact";
    }

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
