package com.example.security_demo;

import java.util.Collection;
import java.util.List;

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
        //return Collections.singleton(new SimpleGrantedAuthority("USER"));
        //return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
        //         return List.of(user.getRole();).stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role.name())).toList();
        Role user_role = user.getRole();
        return List.of(user_role).stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role.name())).toList();
         }
    

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
    
}
