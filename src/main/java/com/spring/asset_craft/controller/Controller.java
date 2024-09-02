package com.spring.asset_craft.controller;

import com.spring.asset_craft.dao.AppDAO;
import com.spring.asset_craft.dto.ProductDTO;
import com.spring.asset_craft.entity.ProductUser;
import com.spring.asset_craft.entity.User;
import com.spring.asset_craft.repository.ProductRepository;
import com.spring.asset_craft.repository.ProductUserRepository;
import com.spring.asset_craft.repository.UserRepository;
import com.spring.asset_craft.service.ProductService;
import com.spring.asset_craft.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static com.spring.asset_craft.entity.ProductUser.ProductUserStatus.CART;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private ProductUserRepository productUserRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;
    private AppDAO appDAO;

    public Controller(AppDAO appDAO) {
        this.appDAO = appDAO;
    }

    //TODO: Make it better
    @GetMapping("/")
    public String showIndex(Model model){

        List<ProductDTO> ProductDTOs = productService.getAllSmallProductsDTO();

        model.addAttribute("products", ProductDTOs);
        return "index";
    }


    @GetMapping("/shop")
    public String showShop(@RequestParam(required = false) String name,
                           @RequestParam(required = false) Float minPrice,
                           @RequestParam(required = false) Float maxPrice,
                           @RequestParam(required = false) Float rating,
                           @RequestParam(required = false) List<String> categories,
                           Model model){

        List<ProductDTO> ProductDTOs = productService.searchProducts(name, minPrice, maxPrice, rating, categories);
        model.addAttribute("products", ProductDTOs);

        float biggestPrice = productService.biggestPrice(ProductDTOs);
        model.addAttribute("biggestPrice", biggestPrice);

        float smallestPrice = productService.smallestPrice(ProductDTOs);
        model.addAttribute("smallestPrice", smallestPrice);

        if (categories == null)
            categories = new ArrayList<>();

        model.addAttribute("categories", categories);

        model.addAttribute("rating", rating);

        model.addAttribute("name", name);

        return "shop";
    }

    @GetMapping("/signInPage")
    public String showSignIn(){  return "signInPage";}

    @GetMapping("/account")
    public String showAccount(Model model, Principal principal){

        // TODO: (HARD) Change approach to use CustomUserDetails and #authentcation
        String username = principal.getName();
        String email = userService.getUserEmail(username);
        model.addAttribute("email", email);
        return "account";
    }

    @GetMapping("/cart")
    public String showCart(Model model, Principal principal){

        model.addAttribute("products", productService.getProductsInCart(principal.getName()));
        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("productId") int productId, Principal principal){

        ProductUser productUser = new ProductUser(
                productService.getProductById(productId),
                userService.getUserByUsername(principal.getName()),
                CART
        );

            productService.addToCart(productUser);

        return "redirect:/shop";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam("productId") int productId, Principal principal){

        productService.deleteFromCart(productId, principal.getName());
        return "redirect:/cart";
    }

    @GetMapping("/contact")
    public String showContact(){
        return "contact";
    }

    @GetMapping("/productPage/{id}")
    public String showProductPage(@PathVariable("id") int id, Model model) {

        ProductDTO ProductDTO = productService.getBigProductDTO(id);
        model.addAttribute("product", ProductDTO);
        return "productPage";
    }

//    TODO: Add user registration and verification
    // TODO: (MAYBE) change user login to JPA/Hibernate instead of JDBC

    // -------------------------------------

    @GetMapping("/admin")
    public String showAdmin(){
        return "admin";
    }

}
