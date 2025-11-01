package noevo.repository;

import java.util.List;
import noevo.model.Conversacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversacionRepository extends JpaRepository<Conversacion, Long> {

    List<Conversacion> findByUsuarioId(Long usuarioId);
    
}
