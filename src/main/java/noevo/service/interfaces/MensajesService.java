package noevo.service.interfaces;

import java.util.List;
import noevo.model.entity.Mensaje;
import noevo.enums.*;

public interface MensajesService {

    // Basicos
    List<Mensaje> findAll();

    void deleteById(Long id);

    // Personalizado
    List<Mensaje> findByConversacionId(Long conversacionId);

    Mensaje crearMensaje(Long conversacionId, String contenido, OpcionesRemitente emisor, OpcionesTipoMensajes tipo);
}
