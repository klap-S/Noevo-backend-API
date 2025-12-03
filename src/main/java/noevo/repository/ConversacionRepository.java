package noevo.repository;

//Java imports
import java.util.List;
import java.util.Optional;

//Org imports
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Noevo imports
import noevo.model.entity.Conversacion;

@Repository
public interface ConversacionRepository extends JpaRepository<Conversacion, Long> {

    // Obtener todas las conversaciones ordenadas por el ultimo acceso
    // descendente(..3,2,1)
    List<Conversacion> findAllByUsuarioIdOrderByLastAccessDesc(Long usuarioId);

    // Buscar la conversacion del usuario por el titulo ignorando mayuscula para
    // mejor busqueda
    List<Conversacion> findAllByUsuarioIdAndTitleContainingIgnoreCase(Long usuarioId, String title);

    // Obtener una conversacion especifica de un usuario y una IA
    Optional<Conversacion> findByIdAndUsuarioIdAndIaId(Long conversacionId, Long usuarioId, Long iaId);

}
