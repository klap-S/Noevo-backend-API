package noevo.model.dto.usuario;

//Java imports
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
public class UsuarioCreateResponseDTO {

    private Long id;

    private String name;

    private String lastNames;

    private String userName;

    private String email;

    private String language;

    private RolUsuario rol;

    private LocalDateTime registrationDate;
}
