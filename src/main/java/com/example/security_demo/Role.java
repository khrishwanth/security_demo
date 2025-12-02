package com.example.security_demo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {

    ADMIN ,
    AGENT , 
    USER 

    ;
    // ADMIN(
    //      Set.of(Role_permissions.ADMIN_ASIGN , Role_permissions.ADMIN_CREATE )
    //     ) ,
    // AGENT (
    //     Set.of(Role_permissions.AGENT_READ)
    // ),
    // USER (Collections.emptySet())
    // ;
    // // @Getter
    // // private final Set<Role_permissions> permissions;

    // @Getter
    // private List<Role> roles;

    // public List<SimpleGrantedAuthority> getAuthority(){
    //  return getPermissions() 
    // .stream()
    // .map(permission -> new SimpleGrantedAuthority(permission.name()))
    // .toList();  
    // }


}
