package com.spring.asset_craft.rest;

import com.spring.asset_craft.entity.ProductUser;
import com.spring.asset_craft.entity.User;
import com.spring.asset_craft.pojo.CartUpdateRequest;
import com.spring.asset_craft.service.UserService;
import com.spring.asset_craft.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

        String username = principal.getName();
        Long userId = userService.getUserByUsername(username).getId();


        productService.addToCart(productId,userId);

        return "redirect:/shop";
    }

    @PostMapping("/product/removeImage")
    public ResponseEntity<?> removeImage(@RequestBody Map<String, Long> request, Principal principal){

        Long imageId = request.get("imageId");
        String username = principal.getName();

        productService.deleteImage(imageId);
        return ResponseEntity.ok().build();
    }


//    TODO: make account(user) deletion work
    @DeleteMapping("/user/delete")
    public ResponseEntity<?> deleteUser( Principal principal) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();

        String username = principal.getName();
        System.out.println(username);
        //User user = userService.getUserByUsername(username);

        //userService.deleteUser(user.getId());
        return ResponseEntity.ok("User deleted");
    }
}
