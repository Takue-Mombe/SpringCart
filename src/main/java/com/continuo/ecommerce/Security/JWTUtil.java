package com.continuo.ecommerce.Security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTUtil {
    private static final String SECRET = "dAmHF1/jnuW1o1oszAAvJHqs/HqVpvLf6Oyo+Cx7bIU=";

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+86400000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }
    String extractEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }
    public boolean validateToken(String token) {
        try{
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build()
                    .parseClaimsJws(token);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
