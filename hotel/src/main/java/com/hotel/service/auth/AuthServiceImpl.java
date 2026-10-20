package com.hotel.service.auth;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hotel.dto.user.AuthResponse;
import com.hotel.dto.user.LoginRequest;
import com.hotel.dto.user.RegistroRequest;
import com.hotel.exception.user.NombreUsuarioNoEncontradoException;
import com.hotel.exception.user.NombreUsuarioRegistroException;
import com.hotel.model.Usuario;
import com.hotel.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse registrar(RegistroRequest request) {
        if (usuarioRepository.findByUsername(request.username()).isPresent()) {
            throw new NombreUsuarioRegistroException(request.username());
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(request.username());
        usuario.setPassword(passwordEncoder.encode(request.password()));
        usuario.setRol(request.rol());
        usuarioRepository.save(usuario);

        UserDetails userDetails = new User(usuario.getUsername(), usuario.getPassword(), List.of());
        String token = jwtService.generarToken(userDetails, usuario.getRol().name());
        return new AuthResponse(token);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        // 1. Verifica username + password (lanza excepción si son incorrectos)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(), request.password()));

        // 2. Si paso, busca el usuario para leer su rol
        Usuario usuario = usuarioRepository.findByUsername(request.username())
                .orElseThrow(() -> new NombreUsuarioNoEncontradoException(request.username()));

        // 3. Genera el token con su rol
        UserDetails userDetails = new User(usuario.getUsername(), usuario.getPassword(), List.of());
        String token = jwtService.generarToken(userDetails, usuario.getRol().name());
        return new AuthResponse(token);
    }
}
