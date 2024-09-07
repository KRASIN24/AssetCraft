package com.spring.asset_craft.service.impl;

import com.spring.asset_craft.dao.AppDAO;
import com.spring.asset_craft.entity.User;
import com.spring.asset_craft.repository.UserRepository;
import com.spring.asset_craft.security.WebUser;
import com.spring.asset_craft.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private AppDAO appDAO;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AppDAO appDAO) {
        this.userRepository = userRepository;
        this.appDAO = appDAO;
    }



    @Override
    public User getUserByUsername(String username){
        return userRepository.findByUsername(username)
                .orElse(null);
    }

    @Override
    public String getUserEmail(String username){
        return userRepository.findByUsername(username)
                .map(User::getEmail)
                .orElse(null);
    }

    @Override
    public void save(WebUser theWebUser) {
        User user = new User();
// assign user details to the user object
        user.setUsername(theWebUser.getUsername());
        //user.setPassword(passwordEncoder.encode(theWebUser.getPassword()));
        String password = "{noop}" + theWebUser.getPassword();
        user.setPassword(password);
        user.setEmail(theWebUser.getEmail());
        user.setActive(true);
        User savedUser = userRepository.save(user);
        userRepository.insertUserRole(savedUser.getId());

    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = appDAO.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                getAthority()
        );
        //mapRolesToAuthorities(user.getRoles()));
    }

//        private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
//
//
//            return //roles.stream().map(role -> new
//                    //SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
//        }

    private Collection<? extends GrantedAuthority> getAthority(){

        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

}
