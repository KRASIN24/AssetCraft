package com.spring.asset_craft.controller;

import com.spring.asset_craft.entity.User;
import com.spring.asset_craft.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private UserService userService;

    @Autowired
    public CustomAuthenticationSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("In customAuthenticationSuccessHandler");
        String username = authentication.getName();
        System.out.println("username=" + username);
        User theUser = userService.getUserByUsername(username);
// now place in the session
        HttpSession session = request.getSession();
        session.setAttribute("user", theUser);
        System.out.println(request.getContextPath() + "/");
// forward to home page
        response.sendRedirect(request.getContextPath() + "/");
    }

}
