package noevo.model.dto.ia;

//Jakata imports
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

//Lombok imports
import lombok.Data;

//Noevo imports
import noevo.enums.OpcionesIdiomas;

@Data
public class IARequestDTO {

    @NotBlank(message = "Introduzca un nombre")
    private String name;

    @NotBlank(message = "Introduzca un modelo")
    private String model;

    @NotNull(message = "Introduzca un idioma")
    private OpcionesIdiomas language;
}
