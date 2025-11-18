package noevo.model.dto.ia;

//Jakata imports
import jakarta.validation.constraints.NotBlank;

//Lombok imports
import lombok.Data;

@Data
public class IARequestDTO {

    @NotBlank(message = "Introduzca un nombre")
    private String name;

    @NotBlank(message = "Introduzca un modelo")
    private String model;

    @NotBlank(message = "Introduzca un idioma")
    private String language;
}
