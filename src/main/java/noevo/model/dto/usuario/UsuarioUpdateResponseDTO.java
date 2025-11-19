package noevo.model.dto.usuario;

//Lombok imports
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Noevo imports
import noevo.enums.OpcionesIdiomas;

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

    private OpcionesIdiomas language;
}
