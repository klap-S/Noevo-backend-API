package noevo.service.interfaces;

//Noevo imports
import noevo.model.dto.usuario.PasswordResetDTO;
import noevo.model.dto.usuario.PasswordUpdateDTO;

public interface PasswordService {

    // Personalizados
    // Actualizar contraseña
    void updatePassword(Long id, PasswordUpdateDTO updateDTO);

    // Resetear Contraseña
    void resetPassword(Long id, PasswordResetDTO passwordResetDTO);

}
