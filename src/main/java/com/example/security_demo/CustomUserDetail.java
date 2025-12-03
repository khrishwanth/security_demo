package com.example.security_demo;

import java.util.ArrayList;
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
        // 1) Role user_role = user.getRole();
        // return List.of(user_role).stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role.name())).toList(); 
        //  user_role.getPermissions().forEach(permission ->  authorities.add(new SimpleGrantedAuthority(permission.name()))
        Role user_role = user.getRole();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
         List<SimpleGrantedAuthority> authority =user_role.getPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission.name())).toList(); // The code in the 3rd comment could have been used to add instead of this line of code and the below loop
         for(SimpleGrantedAuthority user_authority : authority){
            authorities.add(user_authority);
         }
              authorities.add(new SimpleGrantedAuthority("ROLE_"+user_role.name())) ;
     return authorities; 


            // return user.getRole().forEach(role -> role.getAuthority());
         }
    // @Override
    // public Collection<? extends GrantedAuthority> getAuthorities() {
    // Role user_role = user.getRole();
    
    // if (user_role == null) {
    //     return Collections.emptyList();
    // }
    
    // // Combine permissions and role into single stream
    // List<SimpleGrantedAuthority> authorities = Stream.concat(
    //     // Permissions stream
    //     user_role.getPermissions().stream()
    //         .map(permission -> new SimpleGrantedAuthority(permission.name())),
    //     // Role stream (single element)
    //     Stream.of(new SimpleGrantedAuthority("ROLE_" + user_role.name()))
    // ).toList();
    
    // return authorities; }
    

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
    

}