package noevo.model.dto.conversacion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ConversacionRequestDTO {

    @NotBlank(message = "El título")
    private String titulo;

    private String contexto;

    @NotNull(message = "Ia")
    private Long iaId;

    @NotNull(message = "Usuario ")
    private Long usuarioId;

}
