package noevo.service.implement;

//Java imports
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

//Orgs imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Noevo imports
import noevo.model.dto.usuario.UsuarioLoginRequestDTO;
import noevo.model.dto.usuario.UsuarioLoginResponseDTO;
import noevo.model.entity.Usuario;
import noevo.service.interfaces.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UsuarioServiceImpl usuarioServiceImpl;

    // Personalizados
    // Logueo de usuarios
    @Override
    public UsuarioLoginResponseDTO loginUser(UsuarioLoginRequestDTO usuarioLoginRequestDTO) {
        Usuario usuario = usuarioServiceImpl.findByEmail(usuarioLoginRequestDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Correo no encontrado"));

        if (!usuario.getPassword().equals(usuarioLoginRequestDTO.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        usuario.setLastAccess(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        Usuario saved = usuarioServiceImpl.saved(usuario);

        return UsuarioLoginResponseDTO.builder()
                .id(saved.getId())
                .userName(saved.getUserName())
                .email(saved.getEmail())
                .language(saved.getLanguage())
                .rol(saved.getRol())
                .lastAccess(saved.getLastAccess())
                .registrationDate(saved.getRegistrationDate())
                .build();
    }

}
