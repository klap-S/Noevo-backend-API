package noevo.service.interfaces;

//Java imports
import java.util.List;
import java.util.Optional;

//Noevo imports
import noevo.model.entity.IA;
import noevo.model.dto.ia.IARequestDTO;
import noevo.model.dto.ia.IAResponseDTO;

public interface IAService {

    // Basico
    List<IA> findAll();

    Optional<IA> findById(Long id);

    IA saved(IA ia);

    void deleteById(Long id);

    // Personalizado
    // Devuelve DTO Response
    List<IAResponseDTO> findAllResponse();

    // Devuelve DTO Response
    Optional<IAResponseDTO> findByIdResponse(Long id);

    // Devuelve DTO Response
    IAResponseDTO findByNameResponse(String name);

    // Devuelve DTO Response
    IAResponseDTO findByModelResponse(String model);

    // Buscar IA por el nombre
    Optional<IA> findByName(String name);

    // Buscar modelo
    Optional<IA> findByModel(String model);

    // Verificar si existe el nombre
    boolean existsByName(String name);

    // Crear IA
    IAResponseDTO createIA(IARequestDTO iaRequestDTO);

    // Editar IA
    IAResponseDTO updateIA(Long id, IARequestDTO iaRequestDTO);

}
