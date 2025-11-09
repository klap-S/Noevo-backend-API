package noevo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import noevo.model.entity.Conversacion;

@Repository
public interface ConversacionRepository extends JpaRepository<Conversacion, Long> {

    List<Conversacion> findByUsuarioId(Long usuarioId);

    List<Conversacion> findByTitulo(String titulo);

}
