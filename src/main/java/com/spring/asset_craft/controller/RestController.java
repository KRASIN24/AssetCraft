package com.spring.asset_craft.controller;

import com.spring.asset_craft.pojo.CartUpdateRequest;
import com.spring.asset_craft.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Map;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private ProductService productService;

    @PostMapping("/updateCart")
    public String updateCart(@RequestBody CartUpdateRequest request){
        productService.userBought(request.getProductIds());
        return "redirect:/shop";
    }

    @PostMapping("/cart/remove")
    public ResponseEntity<?> removeFromCart(@RequestBody Map<String, Long> request, Principal principal){

        Long productId = request.get("productId");
        String username = principal.getName();

        productService.deleteFromCart(productId, username);
        return ResponseEntity.ok().build();
    }
}
