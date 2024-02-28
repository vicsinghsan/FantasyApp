package com.fantasy.Fantasy.app.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.fantasy.Fantasy.app.model.JWTLogin;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;




@Service
public class JwtUtil {

    private String secret = "Simran";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(JWTLogin jwtDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, jwtDetails);
    }

    private String createToken(Map<String, Object> claims, JWTLogin jwtDetails) {

    	long expirationTime = System.currentTimeMillis() + (1000 * 60 * 30); // 30 minutes from now
        return Jwts.builder()
                .claim("role", jwtDetails.getRole())
                .setSubject(jwtDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(expirationTime))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    
    }
  


    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}


