package noevo.model.dto.usuario;

//Lombok imports
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioUpdateResponseDTO {

    private Long id;

    private String name;

    private String lastNames;

    private String userName;

    private String email;

    private String language;
}
