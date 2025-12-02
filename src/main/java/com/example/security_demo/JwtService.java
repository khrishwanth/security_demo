package com.example.security_demo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
@RestController
public class JwtService {

    private static final String SECRET = "mysupersecretkeymysupersecretkey12";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
    
    public String generateToken(String username){
        Map<String,Object> claim = new HashMap<>();
        Date start = new Date(System.currentTimeMillis());
        Date end = new Date(System.currentTimeMillis() + 60*60*30);
         return Jwts.builder().claims().add(claim).subject(username).issuedAt(start).expiration(end).and().signWith(SECRET_KEY).compact();
    }


    public String extractUserName(String token) {
        // extract the username from jwt token
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

        @GetMapping("auth")
         public String check(String auth) {
           if(auth != null){
            return auth;
           }
             return "failed";
        }

        @GetMapping("name")
        public String get(String auth){
                   return auth;
        }
}
