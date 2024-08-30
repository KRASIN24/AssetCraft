package com.spring.asset_craft.service;

import com.spring.asset_craft.entity.User;
import com.spring.asset_craft.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String getUserEmail(String username){
        return userRepository.findByUsername(username)
                .map(User::getEmail)
                .orElse(null);
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username)
                .orElse(null);
    }

//    public String getUserProfileImage(String username){
//        return userRepository.findByUsername(username)
//                .map()
//    }
}
