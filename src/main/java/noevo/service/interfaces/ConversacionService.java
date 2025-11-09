package noevo.service.interfaces;

import java.util.List;
import java.util.Optional;
import noevo.model.entity.Conversacion;

public interface ConversacionService {

    // Basicos
    List<Conversacion> findAll();

    Optional<Conversacion> findById(Long id);

    Conversacion save(Conversacion conversacion);

    void deleteById(Long id);

    // Personalizado
    List<Conversacion> findByUsuarioId(Long usuarioId);

}
