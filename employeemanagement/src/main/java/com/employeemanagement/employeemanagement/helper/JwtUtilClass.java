package com.employeemanagement.employeemanagement.helper;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtilClass {

    Logger logger= LoggerFactory.getLogger(JwtUtilClass.class);
   @Value("${jwt.secret}")
 private String SECRET_KEY_STRING;
   @Value("${jwt.expiration-ms}")
   private long expiration;

   public String generateToken(UserDetails userDetails){
         String token= Jwts.builder()
                 .setSubject(userDetails.getUsername())
                 .setIssuedAt(new Date(System.currentTimeMillis()))
                 .setExpiration(new Date(System.currentTimeMillis()+expiration))
                 .signWith(Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes()))
                 .compact();
         logger.info("generate token {}",token);
         return token;
   }





   public boolean validateToken(String token,UserDetails userDetails){
       Key key=Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());

       String username=extractUsernameByToken(token);

       Date expirationDate=Jwts.parserBuilder().setSigningKey(key).build()
               .parseClaimsJws(token).getBody().getExpiration();
       return (username.equals(userDetails.getUsername()) && expirationDate.after(new Date()));
   }
   public String extractUsernameByToken(String token){
       Key key=Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());
       return  Jwts.parserBuilder().setSigningKey(key).build()
               .parseClaimsJws(token)
               .getBody().getSubject();
   }
}
