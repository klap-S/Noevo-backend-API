package noevo.service.interfaces;

//Java imports
import java.util.List;
import java.util.Optional;

//Noevo imports
import noevo.model.entity.Mensaje;
import noevo.enums.*;
import noevo.model.dto.mensaje.MensajeRequestDTO;
import noevo.model.dto.mensaje.MensajeResponseDTO;

public interface MensajeService {

    // Basicos
    List<Mensaje> findAll();

    Optional<Mensaje> findById(Long id);

    Mensaje saved(Mensaje mensaje);

    void deleteById(Long id);

    // Personalizado
    // Obtener todos los mensajes de una conversacion
    List<Mensaje> findByConversacionId(Long conversacionId);

    // Obtener mensaje especifico de la conversacion para editar o eliminar
    Optional<Mensaje> findByIdAndConversacionId(Long mensajeId, Long conversacionId);

    // Ordenar Conversacion por orden
    List<Mensaje> findByConversacionIdOrderByOrderAsc(Long conversacionId);

    // Mostrar todos los mensajes de una conversacion para el DTO
    List<MensajeResponseDTO> showMessageConversation(Long conversacionId, Long usuarioId, Long iaId);

    // Crear mensaje
    MensajeResponseDTO createMessage(Long usuarioId, Long iaId, Long conversacionId, OpcionesTipoMensajes tipoMensaje,
            MensajeRequestDTO request);

    // Obtener mensaje especifico de la conversacion para editar
    public MensajeResponseDTO editMessageConversation(Long mensajeId, Long conversacionId, Long usuarioId,
            Long iaId, String newContent);
}
