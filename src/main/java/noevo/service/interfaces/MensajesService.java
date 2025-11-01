package noevo.service.interfaces;

import noevo.model.Mensaje;
import java.util.List;
import java.util.Optional;

public interface MensajesService {

    //Basicos
    List<Mensaje> findAll();
    Optional<Mensaje> findById(Long id);
    Mensaje save (Mensaje mensaje);
    void deleteById(Long id);

    //Personalizado
    List<Mensaje> findByConversacionId(Long conversacionId);
}
