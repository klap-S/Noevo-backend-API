package noevo.model.dto.usuario;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import noevo.enums.RolUsuario;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioCreateResponseDTO {

    private Long id;

    private String nombre;

    private String email;

    private String idioma;

    private RolUsuario rol;

    private LocalDateTime fechaRegistro;
}
