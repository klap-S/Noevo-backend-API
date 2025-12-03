package noevo.model.dto.usuario;

//Jakarta imports
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

//Lombok imports
import lombok.Data;

@Data
public class UsuarioLoginRequestDTO {

    private String email;

    private String password;

}
