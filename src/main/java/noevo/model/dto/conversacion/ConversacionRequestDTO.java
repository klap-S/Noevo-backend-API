package noevo.model.dto.conversacion;

//Java imports
import jakarta.validation.constraints.NotBlank;

//Lombok imports
import lombok.Data;

@Data
public class ConversacionRequestDTO {

    @NotBlank(message = "El título")
    private String title;

    @NotBlank(message = "Resumen breve")
    private String context;

}
