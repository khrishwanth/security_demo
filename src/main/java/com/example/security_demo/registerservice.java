package com.example.security_demo;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.lang.Collections;
@RestController
@Component
// @CrossOrigin(origins = "http://localhost:3000")

public class registerservice {
    @Autowired
    public AuthenticationManager manager;
    @Autowired
    public userRepo repo;
    @Autowired
    public UserDetails detail;
   BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    @PutMapping("reg")
    public String register(@RequestBody users user) throws Useralreadyfoundexception {
        String name = user.getUsername();
        String existingUser =repo.checkUser(name);  // {repo.findByUsername(name).isEmpty();}  Could have used something like this but I Manually wrote the query itself in the repository
        if(name.equals(existingUser))
        {
          return "Username already exists so create a new name";  
        }
        user.setPassword(encoder.encode(user.getPassword()));
         user.setRole(Role.ADMIN);
        repo.save(user);
        return "User registered successfully";
}
      @PutMapping("login")
     public String login(@RequestBody users user){
        String name = user.getUsername();
        String password = user.getPassword();  // Here theres no need for giving the role since AuthenticationManager(manager) calls your loadUserByUsername() which loads the user from database (with role) compares passwords
      Authentication authcredentuials = manager.authenticate(new UsernamePasswordAuthenticationToken(name , password));   // From this line It enters ProviderManager.authenticate() and finds the DaoAuthenticationProvider
      if(authcredentuials.isAuthenticated()){
        JwtService service = new JwtService();
        return service.generateToken(name);
      }
      return "sorry login failed";
         }
         
         @GetMapping("get")
         public List<users> get(){
          return repo.findAll();
         }

@PreAuthorize("hasRole('ADMIN')")
@GetMapping("/roles")
public Collection<? extends GrantedAuthority> getRoles(
        @AuthenticationPrincipal UserDetails userDetails) {
    
    if (userDetails == null) {
        return Collections.emptyList();
    }
    return userDetails.getAuthorities();
}

         @DeleteMapping("del")
         public String del(){
          repo.deleteAll();
          return "All data deleted";
         }
}

