package noevo.model.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioUpdateRequestDTO {

    @NotBlank(message = "Introduzca un nombre")
    private String nombre;

    @Email(message = "El email debe tener un formato valido")
    private String email;

    @NotBlank(message = "Idioma preferido")
    private String idioma;

}
