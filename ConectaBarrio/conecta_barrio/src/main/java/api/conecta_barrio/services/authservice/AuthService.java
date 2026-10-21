package api.conecta_barrio.services.authservice;

import api.conecta_barrio.dto.request.authdto.LoginRequest;
import api.conecta_barrio.dto.request.authdto.RegistroRequest;
import api.conecta_barrio.dto.response.authdto.AuthResponse;

public interface AuthService {

    AuthResponse login(LoginRequest request);

    AuthResponse registrar(RegistroRequest request);

}
