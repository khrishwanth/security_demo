package com.example.security_demo;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetail implements UserDetails {
    @Autowired
    public users user;
    @Autowired
    public customUserDetailsService service;

   public CustomUserDetail(users user){
    this.user = user;
   }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role user_role = user.getRole();
         return user_role.getRoles().stream().map(roles -> new SimpleGrantedAuthority(roles.name())).toList();
         }
         //Collections.singleton(new SimpleGrantedAuthority("USER"));
    

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
    
}
