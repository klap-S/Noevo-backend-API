package noevo.model.dto.mensaje;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import noevo.enums.OpcionesRemitente;
import noevo.enums.OpcionesTipoMensajes;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MensajeResponseDTO {

    private Long id;
    private String contenidoTexto;
    private OpcionesRemitente emisor;
    private OpcionesTipoMensajes tipo;
    private Long conversacionId;
    private LocalDateTime fecha;

}
