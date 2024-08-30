package com.spring.asset_craft.controller;

import com.spring.asset_craft.dao.AppDAO;
import com.spring.asset_craft.dto.BigProductDTO;
import com.spring.asset_craft.dto.MidProductDTO;
import com.spring.asset_craft.dto.SmallProductDTO;
import com.spring.asset_craft.entity.AssociationProductUser;
import com.spring.asset_craft.entity.Product;
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

import static com.spring.asset_craft.entity.AssociationProductUser.ProductUserStatus.CART;

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
        String name = "";
        List<Product> products = appDAO.findProductByName(name);
        List<SmallProductDTO> smallProductDTOS = productService.populateSmallProductDTOS(products);
        // Add the map to the model
        model.addAttribute("products", smallProductDTOS);
        return "index";
    }


    @GetMapping("/shop")
    public String showShop(@RequestParam(required = false) String name,
                           @RequestParam(required = false) Float minPrice,
                           @RequestParam(required = false) Float maxPrice,
                           @RequestParam(required = false) Float rating,
                           @RequestParam(required = false) List<String> categories,
                           Model model){

        List<Product> products = productService.searchProducts(name, minPrice, maxPrice, rating, categories);

        List<SmallProductDTO> smallProductDTOS = productService.populateSmallProductDTOS(products);
        model.addAttribute("products", smallProductDTOS);

        float biggestPrice = productService.biggestPrice(smallProductDTOS);
        model.addAttribute("biggestPrice", biggestPrice);

        float smallestPrice = productService.smallestPrice(smallProductDTOS);
        model.addAttribute("smallestPrice", smallestPrice);

        if (categories == null) {
            categories = new ArrayList<>();
        }

        model.addAttribute("categories", categories);

        model.addAttribute("rating", rating);

        model.addAttribute("name", name);

        return "shop";
    }

    @GetMapping("/signInPage")
    public String showSignIn(){  return "signInPage";
    }

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

        String username = principal.getName();
        List<MidProductDTO> productsInCart = productService.getProductsInCartByUser(username);
        model.addAttribute("products", productsInCart);
        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("productId") int productId, Principal principal){
        AssociationProductUser productUser = new AssociationProductUser();
        productUser.setProduct(productService.getProductById(productId));
        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        productUser.setUser(user);
        productUser.setStatus(CART);
        AssociationProductUser.ProductUserStatus status = CART;
        boolean exists = productUserRepository.alreadyExists(productId, user.getId(), status);
        if(!exists)
            productUserRepository.save(productUser);
        return "redirect:/shop";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam("productId") int productId, Principal principal){
        AssociationProductUser productUser = productUserRepository.findCartProductsByProductId(productId, principal.getName());
        if(productUser != null)
            productUserRepository.delete(productUser);

        return "redirect:/cart";
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

//    TODO: Add user registration and verification
    // TODO: (MAYBE) change user login to JPA/Hibernate instead of JDBC

    // -------------------------------------

    @GetMapping("/admin")
    public String showAdmin(){
        return "admin";
    }

}
