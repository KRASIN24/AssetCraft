package com.spring.asset_craft.controller;

import com.spring.asset_craft.exception.ValidationException;
import com.spring.asset_craft.dto.FormProductDTO;
import com.spring.asset_craft.dto.ProductDTO;
import com.spring.asset_craft.service.UserService;
import com.spring.asset_craft.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

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

    @PostMapping("/add")
    public String addProduct(
            @Valid @ModelAttribute("productForm") FormProductDTO productForm,
            BindingResult bindingResult, Model model, Principal principal,
            RedirectAttributes redirectAttributes) {
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("productForm", productForm);
            return "product/product-form";
        }

        try {
            productService.validateFiles(productForm.getFiles());
            productService.handleProduct(null, productForm,principal);
        }catch (ValidationException e){
            bindingResult.rejectValue("files", "error.files", e.getMessage());
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

        //FIXME: blocker from accepting edited products without images
        if ( productForm.getFiles().isEmpty()){
            //if (productForm.getImages() == null) {
            bindingResult.rejectValue("files", "error.files", "At least one file is required.");
            return "product/product-form";
        }


        try {
            productService.validateFiles(productForm.getFiles());
            productService.handleProduct(id, productForm, principal);
        }catch (ValidationException e){
            bindingResult.rejectValue("files", "error.files", e.getMessage());
            return "product/product-form";
        }


        redirectAttributes.addFlashAttribute("successMessage", "Product added successfully!");
        return "redirect:/account/sold";
    }
}
