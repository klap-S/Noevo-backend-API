package noevo.model.dto.usuario;

//Lombok imports
import lombok.Data;

@Data
public class PasswordResetDTO {

    private String oldPassword;

    private String newPassword;
}
