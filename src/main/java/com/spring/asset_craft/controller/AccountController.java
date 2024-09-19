package com.spring.asset_craft.controller;

import com.spring.asset_craft.dto.PasswordForm;
import com.spring.asset_craft.dto.ReviewDTO;
import com.spring.asset_craft.entity.User;
import com.spring.asset_craft.service.ProductService;
import com.spring.asset_craft.service.UserService;
import com.spring.asset_craft.service.impl.ProductServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static com.spring.asset_craft.entity.ProductUser.ProductUserStatus.*;

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
        return "account/account";
    }

    @GetMapping("/sold")
    public String showSoldAssets(Model model, Principal principal) {
        model.addAttribute("products", productService.getProductsWithStatus(principal.getName(), OWNER));
        return "account/sold-assets";
    }

    @GetMapping("/bought")
    public String showBoughtAssets(Model model, Principal principal) {
        model.addAttribute("products", productService.getProductsWithStatus(principal.getName(), BUYER));
        return "account/bought-assets";
    }

    @GetMapping("/wishlist")
    public String showWhitelistedAssets(Model model, Principal principal) {
        model.addAttribute("products", productService.getProductsWithStatus(principal.getName(), WISHLIST));
        return "account/wishlist-assets";
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/settings/settings")
    public String showUserSettings(Model model, Principal principal) {
        //model.addAttribute("products", productService.getProductsWithStatus(principal.getName(), WISHLIST));
        return "account/settings/settings";
    }

    @GetMapping("/settings/security")
    public String showUserSecurity(Model model, Principal principal) {
        //model.addAttribute("products", productService.getProductsWithStatus(principal.getName(), WISHLIST));

        model.addAttribute("passwordForm", new PasswordForm());
        return "account/settings/security";
    }

    @PostMapping("/settings/security")
    public String changePassword(@ModelAttribute("passwordForm") @Valid PasswordForm passwordForm,
                                 BindingResult bindingResult, Model model, Principal principal) {
        //model.addAttribute("products", productService.getProductsWithStatus(principal.getName(), WISHLIST));

        if (bindingResult.hasErrors()) {
        model.addAttribute("passwordForm", passwordForm);
        return "account/settings/security";
        }

        String username = principal.getName();
        User user = userService.getUserByUsername(username);

        if (!userService.verifyPassword(user,passwordForm.getOldPassword())) {
            bindingResult.rejectValue("oldPassword", "error.oldPassword", "Incorrect old password.");
            return "account/settings/security";
        }

        userService.updatePassword(user, passwordForm.getNewPassword());

        return "redirect:/account?passwordChanged=true";
    }

    @GetMapping("/settings/feedback")
    public String showUserFeedback(Model model, Principal principal) {
        List<ReviewDTO> reviewDTOS = productService.getReviewsDTOByOwner(principal.getName());
        model.addAttribute("reviews", reviewDTOS);
        return "account/settings/feedback";
    }

    @GetMapping("/settings/transactions")
    public String showUserTransactions(Model model, Principal principal) {
        //model.addAttribute("products", productService.getProductsWithStatus(principal.getName(), WISHLIST));
        return "account/settings/transactions";
    }
}
