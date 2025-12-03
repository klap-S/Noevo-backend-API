package noevo.service.implement;

//Org imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Noevo imports
import noevo.service.interfaces.PasswordService;
import noevo.model.dto.usuario.PasswordResetDTO;
import noevo.model.dto.usuario.PasswordUpdateDTO;
import noevo.model.entity.Usuario;

@Service
public class PasswordServiceImpl implements PasswordService {

    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;

    // Personalizados
    // Actualizar contraseña
    public void updatePassword(Long id, PasswordUpdateDTO updateDTO) {
        Usuario usuario = usuarioServiceImpl.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!usuario.getPassword().equals(updateDTO.getOldPassword())) {
            throw new RuntimeException("La contraseña actual es incorrecta");
        }

        usuario.setPassword(updateDTO.getNewPassword());
        usuarioServiceImpl.saved(usuario);
    }

    // Resetear contraseña
    @Override
    public void resetPassword(Long id, PasswordResetDTO passwordResetDTO) {
        Usuario usuario = usuarioServiceImpl.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!usuario.getPassword().equals(passwordResetDTO.getOldPassword())) {
            throw new RuntimeException("La contraseña actual es incorrecta");
        }

        usuario.setPassword(passwordResetDTO.getNewPassword());
        usuarioServiceImpl.saved(usuario);
    }

}
