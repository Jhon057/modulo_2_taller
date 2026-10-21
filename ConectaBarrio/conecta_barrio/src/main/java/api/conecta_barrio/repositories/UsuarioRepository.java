package api.conecta_barrio.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import api.conecta_barrio.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

}
