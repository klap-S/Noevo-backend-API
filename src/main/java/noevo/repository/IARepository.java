package noevo.repository;

import noevo.model.IA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IARepository extends JpaRepository<IA, Long> {

}
