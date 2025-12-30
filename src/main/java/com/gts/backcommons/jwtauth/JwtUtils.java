package com.gts.backcommons.jwtauth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtUtils {

    private final String SECRET_KEY = "celticakeycelticakeycelticakeycelticakey"; // doit être assez long pour HS256

    /** TOKEN CREATION */
    public String generateAccessToken(String username, String companyCode) {
        return createToken(username, companyCode, Constants.TOKEN_VALIDITY);
    }

    public String generateRefreshToken(String username, String companyCode) {
        return createToken(username, companyCode, Constants.REFRESH_TOKEN_VALIDITY);
    }

    public String createToken(String username, String companyCode, long expirationMs) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setSubject(username)
                .claim("companyCode", companyCode)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /** EXTRACTION CLAIMS */
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    private Claims extractAllClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /** VALIDATION TOKEN */
    public boolean validateToken(String token) {
        extractAllClaims(token);
        return true;
    }

    public String extractCompanyCode(String token) {
        return extractClaim(token, claims -> claims.get("companyCode", String.class));
    }
}
