package com.example.security_demo;

import java.util.List;
import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.jsonwebtoken.lang.Collections;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
    ADMIN(
         Set.of(Role_permissions.ADMIN_ASIGN , Role_permissions.ADMIN_CREATE )
        ) ,
    AGENT (
        Set.of(Role_permissions.AGENT_READ)
    ),
    USER (Collections.emptySet())
    ;
    @Getter
    private final Set<Role_permissions> permissions;

    // public List<SimpleGrantedAuthority> getAuthority(){
    //  return getPermissions() 
    // .stream()
    // .map(permission -> new SimpleGrantedAuthority(permission.name()))
    // .toList();  
    // }


}
