package noevo.model.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioCreateRequestDTO {

    @NotBlank(message = "Introduzca un nombre")
    private String nombre;

    @Email(message = "El email debe tener un formato valido")
    private String email;

    @NotBlank(message = "Introduzca una contraseña")
    private String password;

    @NotBlank(message = "Idioma preferido")
    private String idioma;
}
