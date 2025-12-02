package com.example.security_demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class customUserDetailsService implements UserDetailsService {
   
    @Autowired
    public userRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         users user = repo.findByUsername(username).get(0);
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        else{
            return new CustomUserDetail(user);
        }
    }

    
}
