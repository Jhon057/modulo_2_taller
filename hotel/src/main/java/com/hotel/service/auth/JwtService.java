package com.hotel.service.auth;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    // Convierte el texto secreto en la llave que usa jjwt para firmar
    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // Crea un token con el username, el rol, y su fecha de vencimiento
    public String generarToken(UserDetails userDetails, String rol) {
        Instant ahora = Instant.now();
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("rol", rol)
                .issuedAt(Date.from(ahora))
                .expiration(Date.from(ahora.plusMillis(expiration)))
                .signWith(getKey())
                .compact();
    }

    // Saca el username que viaja dentro del token
    public String extraerUsername(String token) {
        return extraerClaims(token).getSubject();
    }

    // Verifica firma + vencimiento leyendo el contenido del token
    private Claims extraerClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // El token es valido si el username coincide y no esta vencido
    public boolean esValido(String token, UserDetails userDetails) {
        String username = extraerUsername(token);
        Instant vencimiento = extraerClaims(token).getExpiration().toInstant();
        boolean vencido = vencimiento.isBefore(Instant.now());
        return username.equals(userDetails.getUsername()) && !vencido;
    }
}
