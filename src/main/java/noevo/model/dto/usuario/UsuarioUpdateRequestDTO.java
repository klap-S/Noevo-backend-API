package noevo.model.dto.usuario;

//Jakarta imports
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

//Lombok imports
import lombok.Data;

//Noevo imports
import noevo.enums.OpcionesIdiomas;

@Data
public class UsuarioUpdateRequestDTO {

    @NotBlank(message = "Introduzca un nombre")
    private String name;

    @NotBlank(message = "Introduzca los apellidos")
    private String lastNames;

    @NotBlank(message = "Introduzca un nombre")
    private String userName;

    @Email(message = "El email debe tener un formato valido")
    private String email;

    @NotNull(message = "Idioma preferido")
    private OpcionesIdiomas language;

}
