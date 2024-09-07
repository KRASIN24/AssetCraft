package com.spring.asset_craft.security;

import com.spring.asset_craft.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Bean
//    public UserDetailsManager userDetailsManager(DataSource dataSource){
//
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT username, password, active FROM user WHERE username=?");
//        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT u.username, r.role_name FROM role r INNER JOIN user u ON r.user_id = u.id WHERE u.username=?");
//        return jdbcUserDetailsManager;
//    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService); //set the custom user details service
        //auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,  AuthenticationSuccessHandler
            customAuthenticationSuccessHandler) throws Exception{

        http
                .authorizeHttpRequests(authorizeRequest -> authorizeRequest
//                        .requestMatchers("/webPath").hasRole("UserRole")
                        .requestMatchers("/", "/shop", "/contact", "/productPage/{id}", "/signUp", "/register/**",
                                "/css/**", "/js/**", "/images/**" ,"/icons/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                                .loginPage("/signIn")
                                .loginProcessingUrl("/authenticateTheUser")
                                //.defaultSuccessUrl("/account")
                                .successHandler(customAuthenticationSuccessHandler)
                                .permitAll()
                )
                .logout(logout -> logout.permitAll()
                )
                .exceptionHandling(authorizeRequest ->
                        authorizeRequest.accessDeniedPage("/access-denied")
                );
        return http.build();
    }
}
