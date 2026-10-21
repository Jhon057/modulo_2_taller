package api.conecta_barrio.services.authservice;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import api.conecta_barrio.dto.request.authdto.LoginRequest;
import api.conecta_barrio.dto.request.authdto.RegistroRequest;
import api.conecta_barrio.dto.response.authdto.AuthResponse;
import api.conecta_barrio.exceptions.UsuarioRegistroException;
import api.conecta_barrio.models.Usuario;
import api.conecta_barrio.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse registrar(RegistroRequest request) {
        if (usuarioRepository.findByUsername(request.username()).isPresent()) {
            throw new UsuarioRegistroException("El username ya esta registrado");
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

    public AuthResponse login(LoginRequest request) {
        // 1. Verifica username + password (lanza excepción si son incorrectos)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(), request.password()));

        // 2. Si paso, busca el usuario para leer su rol
        Usuario usuario = usuarioRepository.findByUsername(request.username())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 3. Genera el token con su rol
        UserDetails userDetails = new User(usuario.getUsername(), usuario.getPassword(), List.of());
        String token = jwtService.generarToken(userDetails, usuario.getRol().name());
        return new AuthResponse(token);
    }
}
