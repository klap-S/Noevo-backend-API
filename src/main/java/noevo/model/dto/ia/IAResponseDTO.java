package noevo.model.dto.ia;

//Lombok imports
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

    private String name;

    private String model;

    private String language;

}
