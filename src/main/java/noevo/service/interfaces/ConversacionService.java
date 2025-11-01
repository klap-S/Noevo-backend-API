package noevo.service.interfaces;

import noevo.model.Conversacion;
import java.util.List;
import java.util.Optional;

public interface ConversacionService {

    //Basicos
    List<Conversacion> findAll();
    Optional<Conversacion> findById(Long id);
    Conversacion save (Conversacion ia);
    void deleteById(Long id);

    //Personalizado
    List<Conversacion> findByUsuarioId(Long usuarioId);
    
}
