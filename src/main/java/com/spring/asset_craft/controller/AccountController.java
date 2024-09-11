package com.spring.asset_craft.controller;

import com.spring.asset_craft.service.ProductService;
import com.spring.asset_craft.service.UserService;
import com.spring.asset_craft.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

import static com.spring.asset_craft.entity.ProductUser.ProductUserStatus.BUYER;
import static com.spring.asset_craft.entity.ProductUser.ProductUserStatus.OWNER;

@Controller
@RequestMapping("/account")
public class AccountController {

    private ProductService productService;
    private UserService userService;

    @Autowired
    public AccountController(ProductServiceImpl productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping
    public String showAccount(Model model, Principal principal){

        // TODO: (HARD) Change approach to use CustomUserDetails and #authentcation
        String username = principal.getName();
        String email = userService.getUserEmail(username);
        model.addAttribute("email", email);
        return "account";
    }

    @GetMapping("/sold")
    public String showSoldAssets(Model model, Principal principal) {
        model.addAttribute("products", productService.getProductsWithStatus(principal.getName(), OWNER));
        return "sold-assets";
    }

    @GetMapping("/bought")
    public String showBoughtAssets(Model model, Principal principal) {
        model.addAttribute("products", productService.getProductsWithStatus(principal.getName(), BUYER));
        return "bought-assets";
    }
}
