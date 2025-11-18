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

    Optional<IA> findByName(String name);

    Optional<IA> findByModel(String model);

    boolean existsByName(String name);

}
