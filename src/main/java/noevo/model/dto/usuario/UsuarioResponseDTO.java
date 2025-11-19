package noevo.model.dto.usuario;

//Java imports
import java.time.LocalDateTime;

//Lombok imports
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

//Noevo imports
import noevo.enums.RolUsuario;
import noevo.enums.OpcionesIdiomas;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponseDTO {

    private Long id;

    private String name;

    private String lastNames;

    private String userName;

    private String email;

    private LocalDateTime registrationDate;

    private LocalDateTime lastAccess;

    private OpcionesIdiomas language;

    private RolUsuario rol;

}
