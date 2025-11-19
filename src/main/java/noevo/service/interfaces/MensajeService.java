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

        /*
         * ====================================
         * Personalizado
         * ====================================
         */
        // Obtener todos los mensajes de una conversacion
        List<Mensaje> findByConversacionId(Long conversacionId);

        // Obtener mensaje especifico de la conversacion para editar o eliminar
        Optional<Mensaje> findByIdAndConversacionId(Long mensajeId, Long conversacionId);

        // Obtener los mensajes de la conversacion y ordenados por orden
        // ascendete(1,2,3...)
        List<Mensaje> findByConversacionIdOrderByOrderAsc(Long conversacionId);

        /*
         * ====================================
         * DTOs
         * ====================================
         */
        // Mostrar todos los mensajes de una conversacion para el DTO
        List<MensajeResponseDTO> showMessageConversation(Long conversacionId, Long usuarioId, Long iaId);

        /*
         * ====================================
         * Desarrollo logica
         * ====================================
         */
        // Crear mensaje
        MensajeResponseDTO createMessage(Long usuarioId, Long iaId, Long conversacionId,
                        OpcionesTipoMensajes tipoMensaje,
                        MensajeRequestDTO mensajeRequestDTO);

        // Obtener mensaje especifico de la conversacion para editar
        public MensajeResponseDTO editMessageConversation(Long mensajeId, Long conversacionId, Long usuarioId,
                        Long iaId, String newContent);

}
