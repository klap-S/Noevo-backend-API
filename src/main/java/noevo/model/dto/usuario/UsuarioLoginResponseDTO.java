package noevo.model.dto.usuario;

import java.time.LocalDateTime;

//Lombok imports
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Noevo imports
import noevo.enums.RolUsuario;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioLoginResponseDTO {

    private Long id;

    private String userName;

    private String email;

    private String language;

    private RolUsuario rol;

    private LocalDateTime lastAccess;

    private LocalDateTime registrationDate;

    private String token;

}
