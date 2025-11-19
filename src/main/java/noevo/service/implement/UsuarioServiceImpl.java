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
import noevo.service.interfaces.UsuarioService;
import noevo.enums.OpcionesIdiomas;
import noevo.enums.RolUsuario;
import noevo.model.dto.usuario.UsuarioCreateRequestDTO;
import noevo.model.dto.usuario.UsuarioCreateResponseDTO;
import noevo.model.dto.usuario.UsuarioResponseDTO;
import noevo.model.dto.usuario.UsuarioUpdateRequestDTO;
import noevo.model.dto.usuario.UsuarioUpdateResponseDTO;
import noevo.model.entity.Usuario;
import noevo.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Basico
    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario saved(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    /*
     * ====================================
     * Personalizado Inicio
     * ====================================
     */
    // Buscar usuario por el nombre
    @Override
    public Optional<Usuario> findByUserName(String userName) {
        return usuarioRepository.findByUserName(userName);
    }

    // Buscar email
    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // Verificar si existe el nombre del usuario
    @Override
    public boolean existsByUserName(String userName) {
        return usuarioRepository.existsByUserName(userName);
    }

    // Verificar si existe el email del usuario
    @Override
    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
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
    // Devuelve DTO Response de todos los usuarios
    @Override
    public List<UsuarioResponseDTO> findAllResponse() {
        List<Usuario> usuarios = findAll();

        return usuarios.stream()
                .map(user -> UsuarioResponseDTO.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .lastNames(user.getLastNames())
                        .userName(user.getUserName())
                        .email(user.getEmail())
                        .registrationDate(user.getRegistrationDate())
                        .lastAccess(user.getLastAccess())
                        .language(user.getLanguage())
                        .rol(user.getRol())
                        .build())
                .toList();
    }

    // Devuelve DTO Response de un usuario por id
    @Override
    public Optional<UsuarioResponseDTO> findByIdResponse(Long id) {
        return findById(id)
                .map(user -> UsuarioResponseDTO.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .lastNames(user.getLastNames())
                        .userName(user.getUserName())
                        .email(user.getEmail())
                        .registrationDate(user.getRegistrationDate())
                        .lastAccess(user.getLastAccess())
                        .language(user.getLanguage())
                        .rol(user.getRol())
                        .build());
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
    // Crear usuario
    @Override
    public UsuarioCreateResponseDTO createUser(UsuarioCreateRequestDTO createUserRequestDTO) {

        if (existsByUserName(createUserRequestDTO.getUserName())) {
            throw new RuntimeException("El nombre de usuario esta registrado");
        }

        if (existsByEmail(createUserRequestDTO.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        Usuario usuario = Usuario.builder()
                .name(createUserRequestDTO.getName())
                .lastNames(createUserRequestDTO.getLastNames())
                .userName(createUserRequestDTO.getUserName())
                .email(createUserRequestDTO.getEmail())
                .password(createUserRequestDTO.getPassword())
                .language(createUserRequestDTO.getLanguage())
                .rol(RolUsuario.NORMAL)
                .registrationDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .build();

        Usuario savedUsuario = saved(usuario);
        return UsuarioCreateResponseDTO.builder()
                .id(savedUsuario.getId())
                .name(savedUsuario.getName())
                .lastNames(savedUsuario.getLastNames())
                .userName(savedUsuario.getUserName())
                .email(savedUsuario.getEmail())
                .registrationDate(savedUsuario.getRegistrationDate())
                .language(savedUsuario.getLanguage())
                .rol(savedUsuario.getRol())
                .build();
    }

    // Crear usuario invitado
    @Override
    public UsuarioResponseDTO createUserInvitado() {

        int numeroAleatorio = (int) (Math.random() * 90 + 10);
        String emailAleatorio = "invitado" + numeroAleatorio + "@noevo.com";

        Usuario usuario = Usuario.builder()
                .name("Invitado")
                .lastNames("Temporal")
                .userName("Invitado")
                .email(emailAleatorio)
                .password("")
                .language(OpcionesIdiomas.INGLES)
                .rol(RolUsuario.INVITADO)
                .registrationDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .build();

        Usuario savedInvitado = saved(usuario);
        return UsuarioResponseDTO.builder()
                .id(savedInvitado.getId())
                .name(savedInvitado.getName())
                .lastNames(savedInvitado.getLastNames())
                .userName(savedInvitado.getUserName())
                .email(savedInvitado.getEmail())
                .language(savedInvitado.getLanguage())
                .rol(savedInvitado.getRol())
                .registrationDate(savedInvitado.getRegistrationDate())
                .lastAccess(savedInvitado.getLastAccess())
                .build();
    }

    // Actualizar usuario
    @Override
    public UsuarioUpdateResponseDTO updateUser(Long id, UsuarioUpdateRequestDTO updateRequestDTO) {
        Usuario usuario = findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.getRol() != RolUsuario.NORMAL) {
            throw new RuntimeException("El usuario no tiene permiso para actualizar.");
        }

        usuario.setName(updateRequestDTO.getName());
        usuario.setLastNames(updateRequestDTO.getLastNames());
        usuario.setUserName(updateRequestDTO.getUserName());
        usuario.setEmail(updateRequestDTO.getEmail());
        usuario.setLanguage(updateRequestDTO.getLanguage());

        Usuario updatedUser = saved(usuario);
        return UsuarioUpdateResponseDTO.builder()
                .id(updatedUser.getId())
                .name(updatedUser.getName())
                .lastNames(updatedUser.getLastNames())
                .userName(updatedUser.getUserName())
                .email(updatedUser.getEmail())
                .language(updatedUser.getLanguage())
                .build();
    }

    /*
     * ====================================
     * Desarrollo logica Fin
     * ====================================
     */
}
