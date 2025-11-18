package noevo.model.dto.conversacion;

//Java imports
import java.time.LocalDateTime;

//Lombok imports
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversacionResponseDTO {

    private Long id;

    private String title;

    private String context;

    private LocalDateTime startDate;

    private Long iaId;

    private Long usuarioId;

    private LocalDateTime lastAccess;

}
