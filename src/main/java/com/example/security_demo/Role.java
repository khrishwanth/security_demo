package com.example.security_demo;

import java.util.Set;

import io.jsonwebtoken.lang.Collections;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {

    // 1) ADMIN ,
    // AGENT , 
    // USER 

    // ;
    
    ADMIN(
         Set.of(Role_permissions.ADMIN_ASIGN , Role_permissions.ADMIN_CREATE)
        ) ,
    AGENT (
        Set.of(Role_permissions.AGENT_READ)
    ),
    USER (Collections.emptySet())
    
    ;
    @Getter
    private final Set<Role_permissions> permissions;



    // public List<SimpleGrantedAuthority> getAuthority(){
    //  List<SimpleGrantedAuthority> authorities = getPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission.name()) ).toList();
    //  authorities.add(new SimpleGrantedAuthority("ROLE"+this.name())) ;
    //  return authorities; 
    // }
}
