package noevo.service.interfaces;

//Java imports
import java.util.List;
import java.util.Optional;

//Noevo imports
import noevo.model.dto.usuario.UsuarioResponseDTO;
import noevo.model.dto.usuario.UsuarioUpdateRequestDTO;
import noevo.model.dto.usuario.UsuarioUpdateResponseDTO;
import noevo.model.dto.usuario.UsuarioCreateRequestDTO;
import noevo.model.dto.usuario.UsuarioCreateResponseDTO;
import noevo.model.entity.Usuario;

public interface UsuarioService {

    // Basico
    List<Usuario> findAll();

    Optional<Usuario> findById(Long id);

    Usuario saved(Usuario usuario);

    void deleteById(Long id);

    /*
     * ====================================
     * Personalizado
     * ====================================
     */
    // Buscar usuario por el nombre
    Optional<Usuario> findByUserName(String userName);

    // Buscar email
    Optional<Usuario> findByEmail(String email);

    // Verificar si existe el nombre del usuario
    boolean existsByUserName(String userName);

    // Verificar si existe el email del usuario
    boolean existsByEmail(String email);

    /*
     * ====================================
     * DTOs
     * ====================================
     */
    // Devuelve DTO Response de todos los usuarios
    List<UsuarioResponseDTO> findAllResponse();

    // Devuelve DTO Response de un usuario por id
    Optional<UsuarioResponseDTO> findByIdResponse(Long id);

    /*
     * ====================================
     * Desarrollo logica
     * ====================================
     */
    // Crear usuario
    UsuarioCreateResponseDTO createUser(UsuarioCreateRequestDTO createUserRequestDTO);

    // Crear usuario invitado
    UsuarioResponseDTO createUserInvitado();

    // Actualizar usuario
    UsuarioUpdateResponseDTO updateUser(Long id, UsuarioUpdateRequestDTO updateRequestDTO);

}
