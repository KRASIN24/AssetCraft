package com.spring.asset_craft.rest;

import com.spring.asset_craft.entity.ProductUser;
import com.spring.asset_craft.pojo.CartUpdateRequest;
import com.spring.asset_craft.service.UserService;
import com.spring.asset_craft.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Map;

import static com.spring.asset_craft.entity.ProductUser.ProductUserStatus.CART;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private UserService userService;

    @PostMapping("/cart/update")
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

    @PostMapping("/cart/add")
    public String addToCart(@RequestBody Map<String, Long> request, Principal principal){

        Long productId = request.get("productId");

        ProductUser productUser = new ProductUser(
                productService.getProductById(productId),
                userService.getUserByUsername(principal.getName()),
                CART
        );

        productService.addToCart(productUser);

        return "redirect:/shop";
    }

    @PostMapping("/product/removeImage")
    public ResponseEntity<?> removeImage(@RequestBody Map<String, Long> request, Principal principal){

        Long imageId = request.get("imageId");
        String username = principal.getName();

        productService.deleteImage(imageId);
        return ResponseEntity.ok().build();
    }
}
