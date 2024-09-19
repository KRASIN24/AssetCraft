package com.spring.asset_craft.security;

import com.spring.asset_craft.entity.User;
import com.spring.asset_craft.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class LoginSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private UserService userService;
    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {

        String username = ((UserDetails) event.getAuthentication().getPrincipal()).getUsername();
        User user = userService.getUserByUsername(username);

        WebAuthenticationDetails authenticationDetails = (WebAuthenticationDetails) event.getAuthentication().getDetails();
        String ipAddress = authenticationDetails.getRemoteAddress();

        userService.saveLoginHistory(user, ipAddress);

    }
}
