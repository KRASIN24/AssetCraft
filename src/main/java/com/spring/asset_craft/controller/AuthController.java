package com.spring.asset_craft.controller;

import com.spring.asset_craft.entity.User;
import com.spring.asset_craft.security.WebUser;
import com.spring.asset_craft.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

// Combined Controller
@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Used to trim input strings to avoid issues with empty spaces
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    // Sign In page
    @GetMapping("/signIn")
    public String showSignIn() {
        return "user/signIn";
    }

    // Sign Up page
    @GetMapping("/signUp")
    public String showSignUp(Model theModel) {
        theModel.addAttribute("webUser", new WebUser());
        return "user/signUp";
    }

    // Process Registration Form
    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(
            @Valid @ModelAttribute("webUser") WebUser theWebUser,
            BindingResult theBindingResult,
            HttpSession session, Model theModel) {

        String userName = theWebUser.getUsername();
        System.out.println("Processing registration form for: " + userName);

        // form validation
        if (theBindingResult.hasErrors()) {
            theModel.addAttribute("webUser", theWebUser);
            return "user/signUp";
        }

        // check the database if user already exists
        User existing = userService.getUserByUsername(userName);
        if (existing != null) {
            theModel.addAttribute("webUser", new WebUser());
            theModel.addAttribute("registrationError", "User name already exists.");
            return "user/signUp";
        }

        // create user account and store in the database
        userService.save(theWebUser);

        System.out.println("Successfully created user: " + userName);

        // place user in the web http session for later use
        session.setAttribute("user", theWebUser);

        return "redirect:/";
    }
}
