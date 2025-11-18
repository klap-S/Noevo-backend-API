package noevo.model.dto.usuario;

//Jakarta imports
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

//Lombok imports
import lombok.Data;

@Data
public class UsuarioCreateRequestDTO {

    @NotBlank(message = "Introduzca un nombre")
    private String name;

    @NotBlank(message = "Introduzca los apellidos")
    private String lastNames;

    @NotBlank(message = "Introduzca un usuario")
    private String userName;

    @Email(message = "El email debe tener un formato valido")
    private String email;

    @NotBlank(message = "Introduzca una contraseña")
    private String password;

    @NotBlank(message = "Idioma preferido")
    private String language;
}
