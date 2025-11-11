package noevo.model.dto.ia;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class IARequestDTO {

    @NotBlank(message = "Introduzca un nombre")
    private String nombre;

    @NotBlank(message = "Introduzca un modelo")
    private String modelo;

    @NotBlank(message = "Introduzca un idioma")
    private String idioma;
}
