package noevo.model.dto.ia;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IAResponseDTO {

    private Long id;

    private String nombre;

    private String modelo;

    private String idioma;

}
