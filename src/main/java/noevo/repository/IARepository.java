package noevo.repository;

//Java imports
import java.util.Optional;

//Org imports
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Noevo imports
import noevo.model.entity.IA;

@Repository
public interface IARepository extends JpaRepository<IA, Long> {

    // Buscar IA por el nombre
    Optional<IA> findByName(String name);

    // Buscar modelo
    Optional<IA> findByModel(String model);

    // Verificar si existe el nombre
    boolean existsByName(String name);

}
