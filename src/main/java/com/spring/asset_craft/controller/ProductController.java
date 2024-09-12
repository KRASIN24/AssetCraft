package com.spring.asset_craft.controller;

import com.spring.asset_craft.dto.FormProductDTO;
import com.spring.asset_craft.dto.ProductDTO;
import com.spring.asset_craft.entity.ProductUser;
import com.spring.asset_craft.entity.User;
import com.spring.asset_craft.service.UserService;
import com.spring.asset_craft.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
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

import static com.spring.asset_craft.entity.ProductUser.ProductUserStatus.*;

@Controller
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;
    private UserService userService;

    @Autowired
    public ProductController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/{productId}")
    public String showProduct(@PathVariable("productId") Long productId, Principal principal, Model model) {

        ProductDTO ProductDTO = productService.getBigProductDTO(productId);

        if (principal != null) {
            String username = principal.getName();
            Long userId = userService.getUserByUsername(username).getId();
            ProductDTO.setInCart(productService.hasStatus(productId, userId, CART));
            ProductDTO.setOwner(productService.hasStatus(productId, userId, OWNER));
            ProductDTO.setBought(productService.hasStatus(productId, userId, BUYER));
        }

        model.addAttribute("product", ProductDTO);
        return "product/product-page";
    }

    // TODO: Maybe rest api
    @PostMapping("/{productId}")
    public String addReview(@PathVariable("productId") Long productId,
                            @RequestParam("review") String review,
                            @RequestParam("rating") Float rating,
                            Principal principal){

        productService.addReview(productId, review, rating, principal.getName());

        return "redirect:/" + productId;
    }


    @GetMapping("/add")
    public String showAddProductForm(Model model, Principal principal) {

        model.addAttribute("productForm", new FormProductDTO());
        return "product/product-form";
    }


    // TODO: make logic responsible for adding products more readable
    @Value("${file.upload-dir}")
    private String uploadDir;
    private static final long MAX_FILE_SIZE = 2 * 1024 * 1024; // 2MB
    @PostMapping("/add")
    public String addProduct(
            //@RequestParam("files") MultipartFile[] files, String name, String category, float price, String description, Model model, Principal principal
            @Valid @ModelAttribute("productForm") FormProductDTO productForm,
            BindingResult bindingResult, Model model, Principal principal,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("productForm", productForm);
            return "product/product-form";
        }

        // Validate file uploads
        if (productForm.getFiles().isEmpty()) {
            bindingResult.rejectValue("files", "error.files", "At least one file is required.");
            return "product/product-form";
        }

        for (MultipartFile file : productForm.getFiles()) {
            if (file.getSize() > MAX_FILE_SIZE) {
                bindingResult.rejectValue("files", "error.files", "File size exceeds the 2MB limit.");
                return "product/product-form";
            }

            // Additional checks for file type, if needed
            if (!file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/png")) {
                bindingResult.rejectValue("files", "error.files", "Only JPEG or PNG images are allowed.");
                return "product/product-form";
            }
        }

        List<MultipartFile> files = productForm.getFiles();
        String name = productForm.getName();
        String category = productForm.getCategory();
        Double price = productForm.getPrice();
        String description = productForm.getDescription();

        List<String> dbPaths = new ArrayList<>();
        String savePath = uploadDir + "products/";

        for (MultipartFile file : files) {
            try {
                Path uploadPath = Paths.get(savePath);
                if(!Files.exists(uploadPath)){
                    Files.createDirectories(uploadPath);
                }

                String fileName = file.getOriginalFilename();

                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                String dbPath ="/images/products/" + fileName;
                dbPaths.add(dbPath);
            }catch (IOException e){
                System.out.println("ERRRRRORRRRRR");
            }
        }

        try {
            productService.addProduct(dbPaths,name,category,price.floatValue(),description,principal);

        } catch (Exception e) {
            bindingResult.rejectValue("files", "error.files", "Failed to upload files.");
            return "product/product-form";
        }
        redirectAttributes.addFlashAttribute("successMessage", "Product added successfully!");
        return "redirect:/account/sold";
    }


    @GetMapping("/edit/{productId}")
    public String editProductForm(@PathVariable("productId") Long id, Principal principal, Model model) {

        FormProductDTO formProductDTO = productService.getFormProductById(id);

        model.addAttribute("productForm", formProductDTO);
        return "product/product-form";
    }

    @PostMapping("/edit/{productId}")
    public String editProduct(@PathVariable("productId") Long id,
                              @Valid @ModelAttribute("productForm") FormProductDTO productForm, BindingResult bindingResult,
                              Principal principal, Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("productForm", productForm);
            return "product/product-form";
        }


        for (MultipartFile file : productForm.getFiles()) {
            if (file.getSize() > MAX_FILE_SIZE) {
                bindingResult.rejectValue("files", "error.files", "File size exceeds the 2MB limit.");
                return "product/product-form";
            }
        }

        //FIXME: blocker from accepting edited products without images
        if ( productForm.getFiles().isEmpty()){
            //if (productForm.getImages() == null) {
            bindingResult.rejectValue("files", "error.files", "At least one file is required.");
            return "product/product-form";
        }

        List<MultipartFile> files = productForm.getFiles();
        String name = productForm.getName();
        String category = productForm.getCategory();
        Double price = productForm.getPrice();
        String description = productForm.getDescription();

        List<String> dbPaths = new ArrayList<>();
        String savePath = uploadDir + "products/";

        for (MultipartFile file : files) {
            try {
                Path uploadPath = Paths.get(savePath);
                if(!Files.exists(uploadPath)){
                    Files.createDirectories(uploadPath);
                }

                String fileName = file.getOriginalFilename();

                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                String dbPath ="/images/products/" + fileName;
                dbPaths.add(dbPath);
            }catch (IOException e){
                System.out.println("ERRRRRORRRRRR");
            }
        }

        try {
            //productService.addProduct(dbPaths,name,category,price.floatValue(),description,principal);
            productService.updateProduct(id, productForm, dbPaths);

        } catch (Exception e) {
            bindingResult.rejectValue("files", "error.files", "Failed to upload files.");
            return "product/product-form";
        }

        redirectAttributes.addFlashAttribute("successMessage", "Product added successfully!");
        return "redirect:/shop";
    }
}
