package noevo.service.interfaces;

import java.util.List;
import noevo.model.entity.Mensaje;
import noevo.enums.*;
import java.util.Optional;

public interface MensajesService {

    // Basicos
    List<Mensaje> findAll();

    Optional<Mensaje> findById(Long id);

    Mensaje save(Mensaje mensaje);

    void deleteById(Long id);

    // Personalizado
    List<Mensaje> findByConversacionId(Long conversacionId);

    Mensaje crearMensaje(Long conversacionId, String contenido, OpcionesRemitente emisor, OpcionesTipoMensajes tipo);
}
