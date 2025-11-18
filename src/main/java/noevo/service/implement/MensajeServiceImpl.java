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

        // Personalizados
        // Obtener mensaje especifico de la conversacion para editar o eliminar
        @Override
        public Optional<Mensaje> findByIdAndConversacionId(Long mensajeId, Long conversacionesId) {
                return mensajeRepository.findByIdAndConversacionId(mensajeId, conversacionesId);
        }

        // Obtener todos los mensajes de una conversacion
        public List<Mensaje> findByConversacionId(Long conversacionId) {
                return mensajeRepository.findByConversacionId(conversacionId);
        }

        // Ordenar Conversacion por orden
        public List<Mensaje> findByConversacionIdOrderByOrderAsc(Long conversacionId) {
                return mensajeRepository.findByConversacionIdOrderByOrderAsc(conversacionId);
        }

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
                                                .speaker(msg.getSpeaker())
                                                .sender(msg.getSender())
                                                .contentText(msg.getContentText())
                                                .type(msg.getType())
                                                .audioUrl(msg.getAudioUrl())
                                                .shippingDate(msg.getShippingDate())
                                                .conversacionId(conversacion.getId())
                                                .build())
                                .toList();
        }

        // Crear mensaje
        @Override
        public MensajeResponseDTO createMessage(Long usuarioId, Long iaId, Long conversacionId,
                        OpcionesTipoMensajes typeMessage, MensajeRequestDTO messageRequestDTO) {
                Usuario usuario = usuarioServiceImpl.findById(usuarioId)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                IA ia = iaServiceImpl.findById(iaId)
                                .orElseThrow(() -> new RuntimeException("IA no encontrada"));

                Conversacion conversacion = conversacionServiceImpl
                                .getConversationUsuario(conversacionId, usuarioId, iaId)
                                .orElse(null);

                if (conversacion == null) {
                        ConversacionRequestDTO conversacionRequestDTO = new ConversacionRequestDTO();
                        conversacionRequestDTO.setTitle("Conversacion_" + (int) (Math.random() * 10000));
                        conversacionRequestDTO.setContext("Contexto_" + (int) (Math.random() * 10000));

                        ConversacionResponseDTO responseDTO = conversacionServiceImpl.createConversacion(usuarioId,
                                        iaId, conversacionRequestDTO);

                        conversacion = Conversacion.builder()
                                        .id(responseDTO.getId())
                                        .usuario(usuario)
                                        .ia(ia)
                                        .title(responseDTO.getTitle())
                                        .context(responseDTO.getContext())
                                        .startDate(responseDTO.getStartDate())
                                        .lastAccess(responseDTO.getLastAccess())
                                        .mensajes(new ArrayList<>())
                                        .build();
                }

                int order = (conversacion.getMensajes() == null) ? 1 : conversacion.getMensajes().size() + 1;

                Mensaje mensaje = Mensaje.builder()
                                .order(order)
                                .speaker(OpcionesRemitente.USUARIO)
                                .sender(OpcionesRemitente.USUARIO)
                                .contentText(messageRequestDTO.getContentText())
                                .type(OpcionesTipoMensajes.TEXTO)
                                .shippingDate(LocalDateTime.now())
                                .conversacion(conversacion)
                                .build();

                Mensaje saved = saved(mensaje);

                conversacion.setLastAccess(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                conversacionServiceImpl.saved(conversacion);
                return MensajeResponseDTO.builder()
                                .id(saved.getId())
                                .order(saved.getOrder())
                                .speaker(saved.getSpeaker())
                                .sender(saved.getSender())
                                .contentText(saved.getContentText())
                                .type(saved.getType())
                                .audioUrl(saved.getAudioUrl())
                                .shippingDate(saved.getShippingDate())
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
                                                "Conversacion no encontrada o no al usuario"));

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

}
