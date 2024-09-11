package com.spring.asset_craft.service;

import com.spring.asset_craft.dao.AppDAO;
import com.spring.asset_craft.entity.Role;
import com.spring.asset_craft.entity.User;
import com.spring.asset_craft.repository.UserRepository;
import com.spring.asset_craft.security.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;


public interface UserService extends UserDetailsService{

    User getUserById(Long userId);
    User getUserByUsername(String username);
    String getUserEmail(String username);
    void save(WebUser theWebUser);

}
