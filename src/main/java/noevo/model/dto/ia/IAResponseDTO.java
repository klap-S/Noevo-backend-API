package noevo.model.dto.ia;

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
public class IAResponseDTO {

    private Long id;

    private String name;

    private String model;

    private OpcionesIdiomas language;

}
