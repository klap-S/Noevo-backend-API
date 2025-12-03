package noevo.service.implement;

//Java imports
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//Org imports
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

//Noevo imports
import noevo.service.interfaces.MensajeService;
import noevo.repository.MensajeRepository;
import noevo.enums.*;
import noevo.model.entity.*;
import noevo.model.dto.conversacion.ConversacionRequestDTO;
import noevo.model.dto.conversacion.ConversacionResponseDTO;
import noevo.model.dto.mensaje.MensajeRequestDTO;
import noevo.model.dto.mensaje.MensajeResponseDTO;

@Service
public class MensajeServiceImpl implements MensajeService {

        @Autowired
        private MensajeRepository mensajeRepository;
        @Autowired
        private ConversacionServiceImpl conversacionServiceImpl;
        @Autowired
        private UsuarioServiceImpl usuarioServiceImpl;
        @Autowired
        private IAServiceImpl iaServiceImpl;

        // Basico
        @Override
        public List<Mensaje> findAll() {
                return mensajeRepository.findAll();
        }

        @Override
        public Optional<Mensaje> findById(Long id) {
                return mensajeRepository.findById(id);
        }

        @Override
        public Mensaje saved(Mensaje mensaje) {
                return mensajeRepository.save(mensaje);
        }

        @Override
        public void deleteById(Long id) {
                mensajeRepository.deleteById(id);
        }

        /*
         * ====================================
         * Personalizado Inicio
         * ====================================
         */
        // Obtener todos los mensajes de una conversacion
        public List<Mensaje> findByConversacionId(Long conversacionId) {
                return mensajeRepository.findByConversacionId(conversacionId);
        }

        // Obtener mensaje especifico de la conversacion para editar o eliminar
        @Override
        public Optional<Mensaje> findByIdAndConversacionId(Long mensajeId, Long conversacionesId) {
                return mensajeRepository.findByIdAndConversacionId(mensajeId, conversacionesId);
        }

        // Obtener los mensajes de la conversacion y ordenados por orden
        // ascendete(1,2,3...)
        public List<Mensaje> findByConversacionIdOrderByOrderAsc(Long conversacionId) {
                return mensajeRepository.findByConversacionIdOrderByOrderAsc(conversacionId);
        }

        /*
         * ====================================
         * Personalizado Fin
         * ====================================
         */

        /*
         * ====================================
         * DTOs Inicio
         * ====================================
         */
        // Mostrar todos los mensajes de una conversacion para el DTO
        @Override
        public List<MensajeResponseDTO> showMessageConversation(Long conversacionId, Long usuarioId, Long iaId) {
                Conversacion conversacion = conversacionServiceImpl
                                .getConversationUsuario(conversacionId, usuarioId, iaId)
                                .orElseThrow(() -> new RuntimeException(
                                                "Conversación no encontrada o no pertenece al usuario"));

                List<Mensaje> mensajes = findByConversacionIdOrderByOrderAsc(conversacionId);

                return mensajes.stream()
                                .map(msg -> MensajeResponseDTO.builder()
                                                .id(msg.getId())
                                                .order(msg.getOrder())
                                                .contentText(msg.getContentText())
                                                .conversacionId(conversacion.getId())
                                                .build())
                                .toList();
        }

        /*
         * ====================================
         * DTOs FIN
         * ====================================
         */

        /*
         * ====================================
         * Desarrollo logica Inicio
         * ====================================
         */
        // Crear mensaje tanto de usuario como de IA
        @Override
        public MensajeResponseDTO createMessage(Long usuarioId, Long iaId, Long conversacionId,
                        OpcionesTipoMensajes typeMessage, OpcionesRemitente typeSpeaker, OpcionesRemitente typeSender,
                        MensajeRequestDTO mensajeRequestDTO) {
                Usuario usuario = usuarioServiceImpl.findById(usuarioId)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                IA ia = iaServiceImpl.findById(iaId)
                                .orElseThrow(() -> new RuntimeException("IA no encontrada"));

                Conversacion conversacion = conversacionServiceImpl
                                .getConversationUsuario(conversacionId, usuarioId, iaId)
                                .orElseThrow(() -> new RuntimeException(
                                                "Conversacion no encontrada"));

                if (conversacion.getMensajes() == null) {
                        conversacion.setMensajes(new ArrayList<>());
                }

                int order = conversacion.getMensajes().size() + 1;

                Mensaje mensaje = Mensaje.builder()
                                .order(order)
                                .speaker(typeSpeaker)
                                .sender(typeSender)
                                .contentText(mensajeRequestDTO.getContentText())
                                .type(typeMessage)
                                .shippingDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                                .conversacion(conversacion)
                                .build();

                Mensaje savedMensaje = saved(mensaje);
                conversacion.setLastAccess(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                conversacion.getMensajes().add(savedMensaje);
                conversacionServiceImpl.saved(conversacion);
                return MensajeResponseDTO.builder()
                                .id(savedMensaje.getId())
                                .order(savedMensaje.getOrder())
                                .speaker(savedMensaje.getSpeaker())
                                .sender(savedMensaje.getSender())
                                .contentText(savedMensaje.getContentText())
                                .type(savedMensaje.getType())
                                .audioUrl(savedMensaje.getAudioUrl())
                                .shippingDate(savedMensaje.getShippingDate())
                                .conversacionId(conversacion.getId())
                                .build();
        }

        // Obtener mensaje especifico de la conversacion para editar
        @Override
        public MensajeResponseDTO editMessageConversation(Long mensajeId, Long conversacionId, Long usuarioId,
                        Long iaId, String newContent) {
                Conversacion conversacion = conversacionServiceImpl
                                .getConversationUsuario(conversacionId, usuarioId, iaId)
                                .orElseThrow(() -> new RuntimeException(
                                                "Conversacion no encontrada o usuario no encontrado"));

                Mensaje mensaje = findByIdAndConversacionId(mensajeId, conversacionId)
                                .orElseThrow(() -> new RuntimeException("Mensaje no encontrado"));

                mensaje.setContentText(newContent);
                mensaje.setShippingDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

                Mensaje updatedMensaje = saved(mensaje);
                return MensajeResponseDTO.builder()
                                .id(updatedMensaje.getId())
                                .order(updatedMensaje.getOrder())
                                .speaker(updatedMensaje.getSpeaker())
                                .sender(updatedMensaje.getSender())
                                .contentText(updatedMensaje.getContentText())
                                .type(updatedMensaje.getType())
                                .audioUrl(updatedMensaje.getAudioUrl())
                                .shippingDate(updatedMensaje.getShippingDate())
                                .conversacionId(conversacion.getId())
                                .build();
        }

        /*
         * ====================================
         * Desarrollo logica Fin
         * ====================================
         */
}
