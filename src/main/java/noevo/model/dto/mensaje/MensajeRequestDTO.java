package noevo.model.dto.mensaje;

//Lombok imports
import lombok.Data;

//Jakarta imports
import jakarta.validation.constraints.NotBlank;

@Data
public class MensajeRequestDTO {

    @NotBlank(message = "Ingrese el texto")
    private String contentText;

}
