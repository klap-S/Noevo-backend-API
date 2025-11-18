package noevo.repository;

//Java imports
import java.util.Optional;
import java.util.List;

//Org imports
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Noevo imports
import noevo.model.entity.Mensaje;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {

    // Obtener todos los mensajes de una conversacion
    List<Mensaje> findByConversacionId(Long conversacionId);

    // Obtener mensaje especifico de la conversacion para editar o eliminar
    Optional<Mensaje> findByIdAndConversacionId(Long mensajeId, Long conversacionId);

    // Ordenar los mensajes
    List<Mensaje> findByConversacionIdOrderByOrderAsc(Long conversacionId);
}
