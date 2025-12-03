package noevo.service.implement;

//Java imports
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

//Org imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Noevo imports
import noevo.model.entity.Conversacion;
import noevo.model.entity.IA;
import noevo.model.entity.Usuario;
import noevo.repository.ConversacionRepository;
import noevo.service.interfaces.ConversacionService;
import noevo.model.dto.conversacion.ConversacionRequestDTO;
import noevo.model.dto.conversacion.ConversacionResponseDTO;
import noevo.model.dto.ia.IAResponseDTO;

@Service
public class ConversacionServiceImpl implements ConversacionService {

    @Autowired
    private ConversacionRepository conversacionRepository;
    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;
    @Autowired
    private IAServiceImpl iaServiceImpl;

    // Basico
    @Override
    public List<Conversacion> findAll() {
        return conversacionRepository.findAll();
    }

    @Override
    public Optional<Conversacion> findById(Long id) {
        return conversacionRepository.findById(id);
    }

    @Override
    public Conversacion saved(Conversacion conversacion) {
        return conversacionRepository.save(conversacion);
    }

    @Override
    public void deleteById(Long id) {
        conversacionRepository.deleteById(id);
    }

    /*
     * ====================================
     * Personalizado Inicio
     * ====================================
     */
    // Mostrar todas las conversaciones ordenadas por el ultimo acceso ascendente
    @Override
    public List<Conversacion> showConversationDesc(Long usuarioId) {
        return conversacionRepository.findAllByUsuarioIdOrderByLastAccessDesc(usuarioId);
    }

    // Buscar la conversacion del usuario por el titulo
    @Override
    public List<Conversacion> findByTitle(Long usuarioId, String title) {
        return conversacionRepository.findAllByUsuarioIdAndTitleContainingIgnoreCase(usuarioId, title);
    }

    // Obtener una conversacion especifica de un usuario y una IA
    @Override
    public Optional<Conversacion> getConversationUsuario(Long conversacionId, Long usuarioId, Long iaId) {
        return conversacionRepository.findByIdAndUsuarioIdAndIaId(conversacionId, usuarioId, iaId);
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
    // Devuelve DTO response todas las conversaciones
    @Override
    public List<ConversacionResponseDTO> findAllResponse() {
        List<Conversacion> conversacion = findAll();

        return conversacion.stream()
                .map(con -> ConversacionResponseDTO.builder()
                        .id(con.getId())
                        .title(con.getTitle())
                        .context(con.getContext())
                        .startDate(con.getStartDate())
                        .iaId(con.getIa().getId())
                        .usuarioId(con.getUsuario().getId())
                        .lastAccess(con.getLastAccess())
                        .build())
                .toList();
    }

    // Devuelve DTO response conversacion por id
    @Override
    public Optional<ConversacionResponseDTO> findByIdResponse(Long id) {
        return findById(id)
                .map(con -> ConversacionResponseDTO.builder()
                        .id(con.getId())
                        .title(con.getTitle())
                        .context(con.getContext())
                        .startDate(con.getStartDate())
                        .iaId(con.getIa().getId())
                        .usuarioId(con.getUsuario().getId())
                        .lastAccess(con.getLastAccess())
                        .build());
    }

    // Mostrar todas las conversaciones ordenadas por el ultimo acceso ascendente en
    // DTO Response
    @Override
    public List<ConversacionResponseDTO> showConversationDescResponse(Long usuarioId) {
        List<Conversacion> conversacion = showConversationDesc(usuarioId);

        return conversacion.stream()
                .map(con -> ConversacionResponseDTO.builder()
                        .id(con.getId())
                        .title(con.getTitle())
                        .iaId(con.getIa().getId())
                        .build())
                .toList();
    }

    /*
     * ====================================
     * DTOs Fin
     * ====================================
     */

    /*
     * ====================================
     * Desarrollo logica Inicio
     * ====================================
     */
    // Crear conversacion
    @Override
    public ConversacionResponseDTO createConversacion(Long usuarioId, Long iaId,
            ConversacionRequestDTO conversacionRequestDTO) {
        Usuario usuario = usuarioServiceImpl.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        IA ia = iaServiceImpl.findById(iaId)
                .orElseThrow(() -> new RuntimeException("IA no encontrada"));

        Conversacion conversacion = Conversacion.builder()
                .title(conversacionRequestDTO.getTitle())
                .context(conversacionRequestDTO.getContext())
                .ia(ia)
                .usuario(usuario)
                .startDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .lastAccess(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .build();

        Conversacion savedConversacion = saved(conversacion);
        return ConversacionResponseDTO.builder()
                .id(savedConversacion.getId())
                .title(savedConversacion.getTitle())
                .context(savedConversacion.getContext())
                .startDate(savedConversacion.getStartDate())
                .iaId(savedConversacion.getIa().getId())
                .usuarioId(savedConversacion.getUsuario().getId())
                .lastAccess(savedConversacion.getLastAccess())
                .build();
    }

    // Editar Conversacion
    @Override
    public ConversacionResponseDTO updateConversacion(Long conversacionId, Long usuarioId, Long iaId,
            ConversacionRequestDTO conversacionRequestDTO) {

        Conversacion conversacion = getConversationUsuario(conversacionId, usuarioId, iaId)
                .orElseThrow(() -> new RuntimeException("Conversación no encontrada o no pertenece al usuario"));

        conversacion.setTitle(conversacionRequestDTO.getTitle());
        conversacion.setContext(conversacionRequestDTO.getContext());

        Conversacion updatedConversacion = saved(conversacion);
        return ConversacionResponseDTO.builder()
                .id(updatedConversacion.getId())
                .title(updatedConversacion.getTitle())
                .context(updatedConversacion.getContext())
                .startDate(updatedConversacion.getStartDate())
                .iaId(updatedConversacion.getIa().getId())
                .usuarioId(updatedConversacion.getUsuario().getId())
                .lastAccess(updatedConversacion.getLastAccess())
                .build();
    }

    /*
     * ====================================
     * Desarrollo logica Fin
     * ====================================
     */
}
