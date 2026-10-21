package api.conecta_barrio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import api.conecta_barrio.models.Solicitud;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

    boolean existsByIgnoringCaseTitulo(String titulo);

    boolean existsByTituloAndIdNot(String titulo, Long id);

}
