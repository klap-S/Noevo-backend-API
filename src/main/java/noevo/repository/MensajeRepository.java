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

    // Obtener mensaje especifico de la conversacion para editar
    Optional<Mensaje> findByIdAndConversacionId(Long mensajeId, Long conversacionId);

    // Obtener los mensajes de la conversacion y ordenados por orden
    // ascendete(1,2,3...)
    List<Mensaje> findByConversacionIdOrderByOrderAsc(Long conversacionId);
}
