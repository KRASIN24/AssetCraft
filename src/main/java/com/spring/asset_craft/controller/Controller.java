package com.spring.asset_craft.controller;

import com.spring.asset_craft.dao.AppDAO;
import com.spring.asset_craft.entity.Product;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {

    private AppDAO appDAO;

    public Controller(AppDAO appDAO) {
        this.appDAO = appDAO;
    }

    @GetMapping("/")
    public String showIndex(Model model){
        String name = "";
        List<Product> products = appDAO.findProductByName(name);
        model.addAttribute("products", products);
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
