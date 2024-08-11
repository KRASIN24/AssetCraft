package com.spring.asset_craft.controller;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller {

    @GetMapping("/")
    public String showIndex(){
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

    // -------------------------------------

    @GetMapping("/admin")
    public String showAdmin(){
        return "admin";
    }


    @GetMapping("/login")
    public String showLogin(){
        return "login";
    }

}
