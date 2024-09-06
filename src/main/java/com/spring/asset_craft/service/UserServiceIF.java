package com.spring.asset_craft.service;

import com.spring.asset_craft.entity.User;
import com.spring.asset_craft.security.WebUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserServiceIF extends UserDetailsService {

     User getUserByUsername(String username);
     String getUserEmail(String username);
     void save(WebUser theWebUser);
}
