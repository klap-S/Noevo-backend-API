package noevo.model.dto.mensaje;

//Java imports
import java.time.LocalDateTime;

//Lombok imports
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Noevo imports
import noevo.enums.OpcionesRemitente;
import noevo.enums.OpcionesTipoMensajes;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MensajeResponseDTO {

    private Long id;

    private Integer order;

    private OpcionesRemitente speaker;

    private OpcionesRemitente sender;

    private String contentText;

    private OpcionesTipoMensajes type;

    private String audioUrl;

    private LocalDateTime shippingDate;

    private Long conversacionId;

}
