package com.spring.asset_craft.controller;

import com.spring.asset_craft.security.WebUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/signIn")
    public String showSignIn(){  return "user/signIn";}


    //    TODO: Finish user registration and Add verification
    @GetMapping("/signUp")
    public String showSignUp(Model theModel){
        theModel.addAttribute("webUser", new WebUser());
        return "user/signUp";
    }
}
