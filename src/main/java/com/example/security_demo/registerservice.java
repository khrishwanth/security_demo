package com.example.security_demo;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
@Component
// @CrossOrigin(origins = "http://localhost:3000")

public class registerservice {
    @Autowired
    public AuthenticationManager manager;
    @Autowired
    public userRepo repo;
   BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    @PutMapping("reg")
    public String register(@RequestBody users user) throws Useralreadyfoundexception {
        String name = user.getUsername();
        String existingUser =repo.checkUser(name);  // {repo.findByUsername(name).isEmpty();}  Could have used something like this but I Manually wrote the query itself in the repository
        if(existingUser == null){  
          //BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
          user.setPassword(encoder.encode(user.getPassword()));
           repo.save(user);
        }
        else if(name.equals(existingUser)){
            return "Username already exists so create a new name";
        }
          return "User registered successfully";
}
      @PutMapping("login")
     public String login(@RequestBody users user){
        String name = user.getUsername();
        String password = user.getPassword();
      Authentication authcredentuials = manager.authenticate(new UsernamePasswordAuthenticationToken(name , password));
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

          @PostMapping("roles")
         public List<SimpleGrantedAuthority> wet(@RequestBody users user){
          return user.getRole().getPermissions().stream().map(Permission -> new SimpleGrantedAuthority(Permission.name())).toList();
         }
         @DeleteMapping("del")
         public String del(){
          repo.deleteAll();
          return "All data deleted";
         }
}

