package api.conecta_barrio.services.authservice;

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

    // Convierte el secreto en una llave para firmar el JWT
    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8));
    }

    // Genera el JWT
    public String generarToken(UserDetails userDetails, String rol) {

        Instant ahora = Instant.now();

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("rol", rol)
                .issuedAt(Date.from(ahora))
                .expiration(Date.from(
                        ahora.plusMillis(expiration)))
                .signWith(getKey())
                .compact();
    }

    // Extrae el username del JWT
    public String extraerUsername(String token) {
        return extraerClaims(token).getSubject();
    }

    // Extrae y valida los claims del JWT
    private Claims extraerClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Verifica que el token pertenezca al usuario y no esté vencido
    public boolean esValido(String token, UserDetails userDetails) {

        String username = extraerUsername(token);

        Instant vencimiento = extraerClaims(token)
                .getExpiration()
                .toInstant();

        return username.equals(userDetails.getUsername())
                && vencimiento.isAfter(Instant.now());
    }
}
