package noevo.repository;

import noevo.model.IA;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IARepository extends CrudRepository<IA, Long> {

}
