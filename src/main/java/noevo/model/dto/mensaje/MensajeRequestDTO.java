package noevo.model.dto.mensaje;

import lombok.Data;
import noevo.enums.OpcionesRemitente;
import noevo.enums.OpcionesTipoMensajes;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class MensajeRequestDTO {

    @NotBlank(message = "Ingrese el texto")
    private String contenidoTexto;

    @NotNull(message = "Conversacion el id")
    private Long conversacionId;

    @NotNull(message = "Quien lo envia")
    private OpcionesRemitente emisor;

    private OpcionesTipoMensajes tipo;

}
