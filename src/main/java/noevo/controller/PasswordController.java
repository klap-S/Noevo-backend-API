package noevo.controller;

//Org imports
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

//Noevo imports
import noevo.model.dto.usuario.PasswordResetDTO;
import noevo.model.dto.usuario.PasswordUpdateDTO;
import noevo.service.implement.PasswordServiceImpl;

@RestController
@RequestMapping("/backend/api/password")
public class PasswordController {

        @Autowired
        private PasswordServiceImpl passwordServiceImpl;

        // Actualizar contraseña de usuario
        @PutMapping("/update/{id}")
        public void updatePassword(@PathVariable Long id, @RequestBody PasswordUpdateDTO updateDTO) {
                passwordServiceImpl.updatePassword(id, updateDTO);
        }

        // Resetear contraseña por si se olvida
        @PutMapping("/reset/{id}")
        public void resetPassword(@PathVariable Long id, @RequestBody PasswordResetDTO passwordResetDTO) {
                passwordServiceImpl.resetPassword(id, passwordResetDTO);
        }
}
