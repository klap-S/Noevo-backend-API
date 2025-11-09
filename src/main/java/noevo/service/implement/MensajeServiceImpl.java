package noevo.service.implement;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import noevo.model.entity.Mensaje;
import noevo.model.entity.Conversacion;
import noevo.repository.ConversacionRepository;
import noevo.repository.MensajeRepository;
import noevo.service.interfaces.MensajesService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import noevo.enums.*;

@Service
public class MensajeServiceImpl implements MensajesService {

    @Autowired
    private MensajeRepository mensajeRepository;
    @Autowired
    private ConversacionRepository conversacionRepository;

    // Basico
    @Override
    public List<Mensaje> findAll() {
        return mensajeRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        mensajeRepository.deleteById(id);
    }

    // Personalizados
    @Override
    public List<Mensaje> findByConversacionId(Long conversacionId) {
        return mensajeRepository.findByConversacionId(conversacionId);
    }

    @Override
    public Mensaje crearMensaje(Long conversacionId, String contenido, OpcionesRemitente emisor,
            OpcionesTipoMensajes tipo) {
        Conversacion conversacion = conversacionRepository.findById(conversacionId)
                .orElseThrow(() -> new RuntimeException("La conversacion no se ha encontrado"));
        Mensaje mensaje = new Mensaje();
        mensaje.setContenidoTexto(contenido);
        mensaje.setConversacion(conversacion);
        mensaje.setFecha(LocalDateTime.now());
        mensaje.setEmisor(emisor);
        mensaje.setRemitente(emisor);
        mensaje.setTipo(tipo);
        mensaje.setOrden(conversacion.getMensajes().size() + 1);

        return mensajeRepository.save(mensaje);
    }

}
