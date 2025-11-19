package noevo.model.dto.usuario;

//Jakarta imports
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

//Lombok imports
import lombok.Data;

@Data
public class UsuarioLoginRequestDTO {

    @Email(message = "El email debe tener un formato valido")
    private String email;

    @NotBlank(message = "Introduzca una contraseña")
    private String password;

}
