package noevo.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import noevo.model.entity.IA;

@Repository
public interface IARepository extends JpaRepository<IA, Long> {

    Optional<IA> findByNombre(String nombre);

    Optional<IA> findByModelo(String modelo);

    boolean existsByNombre(String nombre);

}
