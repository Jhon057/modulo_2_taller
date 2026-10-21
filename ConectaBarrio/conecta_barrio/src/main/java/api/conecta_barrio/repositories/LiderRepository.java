package api.conecta_barrio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import api.conecta_barrio.models.Lider;

public interface LiderRepository extends JpaRepository<Lider, Long> {

    boolean existsByIgnoreCaseEmail(String email);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);

}
