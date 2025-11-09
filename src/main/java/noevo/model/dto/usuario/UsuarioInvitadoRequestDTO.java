package noevo.model.dto.usuario;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

public class UsuarioInvitadoRequestDTO {

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fechaRegistro;
}
