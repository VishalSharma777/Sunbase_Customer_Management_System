package com.example.demo.customerServices;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${security.jwt.secret-key}") // Injects the JWT secret key from application properties
    private String secretKey;

    @Value("${security.jwt.expiration-time}") // Injects the JWT expiration time from application properties
    private long jwtExpiration;

    @jakarta.annotation.PostConstruct
    public void validateConfig() {
        // Ensures that the secret key and expiration time are properly configured
        if (secretKey == null || secretKey.isEmpty()) {
            throw new IllegalStateException("JWT secret key must be configured");
        }
        if (jwtExpiration <= 0) {
            throw new IllegalStateException("JWT expiration time must be positive");
        }
    }

    // Extracts the username (subject) from the JWT token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extracts a specific claim from the JWT token using the provided function
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Generates a JWT token for the given user details with default expiration time
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    // Generates a JWT token with additional claims and specified expiration time
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    // Returns the expiration time of the JWT token
    public long getExpirationTime() {
        return jwtExpiration;
    }

    // Builds the JWT token with specified claims, user details, and expiration time
    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims) // Sets the claims (e.g., roles, permissions)
                .setSubject(userDetails.getUsername()) // Sets the subject (username)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Sets the issued date
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // Sets the expiration date
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // Signs the token with the secret key
                .compact();
    }

    // Validates the JWT token by checking the username and expiration
    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token); // Extracts the username from the token
            return (username.equals(userDetails.getUsername())) && !isTokenExpired(token); // Checks if the token is valid
        } catch (Exception e) {
            // Log the exception or handle it according to your needs
            return false;
        }
    }

    // Checks if the token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extracts the expiration date from the token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extracts all claims from the JWT token
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey()) // Sets the key for parsing
                .build()
                .parseClaimsJws(token) // Parses the token
                .getBody(); // Retrieves the claims from the token
    }

    // Converts the base64-encoded secret key into a Key object for signing the JWT
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
