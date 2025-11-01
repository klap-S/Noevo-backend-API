package noevo.repository;

import noevo.model.Conversacion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversacionRepository extends CrudRepository<Conversacion, Long> {

}
