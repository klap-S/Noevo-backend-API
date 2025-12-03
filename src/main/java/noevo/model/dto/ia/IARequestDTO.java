package noevo.model.dto.ia;

//Jakata imports
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

//Lombok imports
import lombok.Data;

//Noevo imports
import noevo.enums.OpcionesIdiomas;
import noevo.enums.RolIA;

@Data
public class IARequestDTO {

    @NotBlank(message = "Introduzca un nombre")
    private String name;

    @NotBlank(message = "Introduzca un rol")
    private RolIA rol;

    @NotNull(message = "Introduzca un idioma")
    private OpcionesIdiomas language;
}
