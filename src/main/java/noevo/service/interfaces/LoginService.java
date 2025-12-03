package noevo.service.interfaces;

//Jakarta imports
import jakarta.servlet.http.HttpServletRequest;

//Noevo imports
import noevo.model.dto.usuario.UsuarioLoginRequestDTO;
import noevo.model.dto.usuario.UsuarioLoginResponseDTO;

public interface LoginService {

    // Personalizados
    // Logueo de usuarios
    UsuarioLoginResponseDTO loginUser(UsuarioLoginRequestDTO usuarioLoginDTO, HttpServletRequest servletRequest);
}
