package noevo.model.dto.ia;

//Lombok imports
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Noevo imports
import noevo.enums.OpcionesIdiomas;
import noevo.enums.RolIA;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IAResponseDTO {

    private Long id;

    private String name;

    private RolIA rol;

    private OpcionesIdiomas language;

}
