package com.rzdp.winestoreapi.security;

import com.rzdp.winestoreapi.config.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final JwtProperties jwtProperties;

    @Autowired
    public JwtProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public String generateJwt(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getUserId()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtProperties.getExpirationInMillis()))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret())
                .compact();
    }

    public Long getUserIdFromJwt(String token) {
        Claims claims = getJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }

    public boolean validateJwt(String jwt) {
        try {
            getJws(jwt);
            return true;
        } catch (SignatureException e) {
            log.error("Jwt signature is invalid!");
        } catch (MalformedJwtException e) {
            log.error("Jwt is invalid!");
        } catch (ExpiredJwtException e) {
            log.error("Jwt is expired!");
        } catch (UnsupportedJwtException e) {
            log.error("Jwt is unsupported!");
        } catch (IllegalArgumentException e) {
            log.error("Jwt claims is empty!");
        }
        return false;
    }

    private Jws<Claims> getJws(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecret())
                .parseClaimsJws(token);
    }
}
