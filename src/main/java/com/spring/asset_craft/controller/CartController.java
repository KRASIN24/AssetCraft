package com.spring.asset_craft.controller;

import com.spring.asset_craft.entity.ProductUser;
import com.spring.asset_craft.service.ProductService;
import com.spring.asset_craft.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

import static com.spring.asset_craft.entity.ProductUser.ProductUserStatus.CART;

@Controller
@RequestMapping("/cart")
public class CartController {

    private ProductService productService;
    private UserService userService;

    @Autowired
    public CartController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping
    public String showCart(Model model, Principal principal){

//        List<ProductDTO>  products = productService.getProductsInCart(principal.getName());
//        for (ProductDTO product : products
//             ) {
//            System.out.println(product.toString());
//        }
        model.addAttribute("products", productService.getProductsWithStatus(principal.getName(), CART));
        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") Long productId, Principal principal){

        String username = principal.getName();
        Long userId = userService.getUserByUsername(username).getId();


        productService.addToCart(productId,userId);

        return "redirect:/shop";
    }
}
