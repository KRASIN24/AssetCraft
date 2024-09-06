package com.spring.asset_craft.controller;

import com.spring.asset_craft.dao.AppDAO;
import com.spring.asset_craft.dto.ProductDTO;
import com.spring.asset_craft.entity.ProductUser;
import com.spring.asset_craft.entity.User;
import com.spring.asset_craft.repository.ProductRepository;
import com.spring.asset_craft.repository.ProductUserRepository;
import com.spring.asset_craft.repository.UserRepository;
import com.spring.asset_craft.security.WebUser;
import com.spring.asset_craft.service.ProductService;
import com.spring.asset_craft.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
    public String showIndex(Model model,Principal principal){

        List<ProductDTO> ProductDTOs = productService.getAllSmallProductsDTO();

        if (principal != null) {
            // FIXME: !!! Change this monstrosity
            User user = userService.getUserByUsername(principal.getName());
            for (ProductDTO product : ProductDTOs) {
                ProductUser productUser = new ProductUser(
                        productService.getProductById(product.getId()),
                        user,
                        CART
                );
                product.setOwner(productService.isOwner(productUser));
                product.setInCart(productService.isInCart(productUser));
                product.setBought(productService.isBought(productUser));
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
            // FIXME: !!! Change this monstrosity
            User user = userService.getUserByUsername(principal.getName());
            for (ProductDTO product : ProductDTOs) {
                ProductUser productUser = new ProductUser(
                        productService.getProductById(product.getId()),
                        user,
                        CART
                );
                product.setOwner(productService.isOwner(productUser));
                product.setInCart(productService.isInCart(productUser));
                product.setBought(productService.isBought(productUser));
            }
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

        return "shop";
    }

    @GetMapping("/signIn")
    public String showSignIn(){  return "signIn";}

    @GetMapping("/signUp")
    public String showSignUp(Model theModel){
        theModel.addAttribute("webUser", new WebUser());
        return "signUp";
    }

    @GetMapping("/account")
    public String showAccount(Model model, Principal principal){

        // TODO: (HARD) Change approach to use CustomUserDetails and #authentcation
        String username = principal.getName();
        String email = userService.getUserEmail(username);
        model.addAttribute("email", email);
        return "account";
    }
    @GetMapping("/account/addProduct")
    public String showAddProductForm(Model model, Principal principal) {
        return "add-product-form";
    }

    // TODO: make logic responsible for adding products more readable
    @Value("${file.upload-dir}")
    private String uploadDir;
    @PostMapping("/account/addProduct")
    public String addProduct(@RequestParam("files") MultipartFile[] files,
                             String name, String category, float price, String description, Model model, Principal principal) {



        List<String> dbPaths = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                Path uploadPath = Paths.get(uploadDir);
                if(!Files.exists(uploadPath)){
                    Files.createDirectories(uploadPath);
                }

                String fileName = file.getOriginalFilename();

                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                String dbPath = "/images/temp/" + fileName;
                dbPaths.add(dbPath);
            }catch (IOException e){
                System.out.println("ERRRRRORRRRRR");
            }
        }
        productService.addProduct(dbPaths,name,category,price,description,principal);

        return "redirect:/account/sold";
    }

    @GetMapping("/account/sold")
    public String showSoldAssets(Model model, Principal principal) {
        model.addAttribute("products", productService.getSoldProducts(principal.getName()));
        return "sold-assets";
    }

    @GetMapping("/account/bought")
    public String showBoughtAssets(Model model, Principal principal) {
        model.addAttribute("products", productService.getBoughtProducts(principal.getName()));
        return "bought-assets";
    }

    @GetMapping("/cart")
    public String showCart(Model model, Principal principal){

        model.addAttribute("products", productService.getProductsInCart(principal.getName()));
        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("productId") Long productId, Principal principal){

        ProductUser productUser = new ProductUser(
                productService.getProductById(productId),
                userService.getUserByUsername(principal.getName()),
                CART
        );

            productService.addToCart(productUser);

        return "redirect:/shop";
    }

    @GetMapping("/contact")
    public String showContact(){
        return "contact";
    }

    @GetMapping("/productPage/{productId}")
    public String showProductPage(@PathVariable("productId") Long productId, Principal principal, Model model) {

        ProductDTO ProductDTO = productService.getBigProductDTO(productId);

        if (principal != null) {
            // FIXME: !!! Change this monstrosity
            User user = userService.getUserByUsername(principal.getName());
                ProductUser productUser = new ProductUser(
                        productService.getProductById(ProductDTO.getId()),
                        user,
                        CART
                );
            ProductDTO.setOwner(productService.isOwner(productUser));
            ProductDTO.setInCart(productService.isInCart(productUser));
            ProductDTO.setBought(productService.isBought(productUser));
            }

        model.addAttribute("product", ProductDTO);
        return "productPage";
    }

    @PostMapping("/productPage/{productId}")
    public String addReview(@PathVariable("productId") Long productId,
                            @RequestParam("review") String review,
                            @RequestParam("rating") Float rating,
                            Principal principal){
        productService.addReview(productId, review, rating, principal.getName());

        return "redirect:/productPage/" + productId;
    }

    //    TODO: Add user registration and verification
    // TODO: (MAYBE) change user login to JPA/Hibernate instead of JDBC

    // -------------------------------------

    @GetMapping("/admin")
    public String showAdmin(){
        return "admin";
    }

}
