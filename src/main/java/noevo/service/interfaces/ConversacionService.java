package noevo.service.interfaces;

//Java imports
import java.util.List;
import java.util.Optional;

//Noevo imports
import noevo.model.entity.Conversacion;
import noevo.model.dto.conversacion.ConversacionRequestDTO;
import noevo.model.dto.conversacion.ConversacionResponseDTO;

public interface ConversacionService {

        // Basicos
        List<Conversacion> findAll();

        Optional<Conversacion> findById(Long id);

        Conversacion saved(Conversacion conversacion);

        void deleteById(Long id);

        /*
         * ====================================
         * Personalizado
         * ====================================
         */
        // Mostrar todas las conversaciones ordenadas por el ultimo acceso ascendente
        List<Conversacion> showConversationDesc(Long usuarioId);

        // Buscar la conversacion del usuario por el titulo
        List<Conversacion> findByTitle(Long usuarioId, String title);

        // Obtener una conversacion especifica de un usuario y una IA
        Optional<Conversacion> getConversationUsuario(Long conversacionId, Long usuarioId, Long iaId);

        /*
         * ====================================
         * DTOs
         * ====================================
         */
        // Devuelve DTO response todas las conversaciones
        List<ConversacionResponseDTO> findAllResponse();

        // Devuelve DTO response conversacion por id
        Optional<ConversacionResponseDTO> findByIdResponse(Long id);

        /*
         * ====================================
         * Desarrollo logica
         * ====================================
         */
        // Crear conversacion
        ConversacionResponseDTO createConversacion(Long usuarioId, Long iaId,
                        ConversacionRequestDTO conversacionRequestDTO);

        // Editar Conversacion
        ConversacionResponseDTO updateConversacion(Long conversacionId, Long usuarioId, Long iaId,
                        ConversacionRequestDTO conversacionRequestDTO);
}
