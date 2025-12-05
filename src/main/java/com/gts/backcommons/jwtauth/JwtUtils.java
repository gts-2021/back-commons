package com.gts.backcommons.jwtauth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtils {

    private final String SECRET_KEY = "celticakey";

    /** TOKEN CREATION */
    public String generateAccessToken(String username, String companyCode) {
        return createToken(username, companyCode, Constants.TOKEN_VALIDITY);
    }

    public String generateRefreshToken(String username, String companyCode) {
        return createToken(username, companyCode, Constants.REFRESH_TOKEN_VALIDITY);
    }

    public String createToken(String username, String companyCode, long expirationMs) {
        return Jwts.builder()
                .setSubject(username)
                .claim("companyCode", companyCode)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /** EXTRACTION CLAIMS */
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    /* this method can throw 'ExpiredJwtException', 'SignatureException' , 'MalformedJwtException' or 'IllegalArgumentException'
     exceptions all these exception are treated in GlobalExceptionHandler
    */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
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

