package noevo.model.dto.usuario;

import lombok.Data;

@Data
public class PasswordResetDTO {

    private String password;

    private String confirmPassword;
}
