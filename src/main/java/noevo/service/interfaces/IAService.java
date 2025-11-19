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

    /*
     * ====================================
     * Personalizado
     * ====================================
     */
    // Buscar IA por el nombre
    Optional<IA> findByName(String name);

    // Buscar modelo
    Optional<IA> findByModel(String model);

    // Verificar si existe el nombre
    boolean existsByName(String name);

    /*
     * ====================================
     * DTOs
     * ====================================
     */
    // Devuelve DTO Response de todos las IA
    List<IAResponseDTO> findAllResponse();

    // Devuelve DTO Response de una IA por id
    Optional<IAResponseDTO> findByIdResponse(Long id);

    // Devuelve DTO Response del nombre IA
    IAResponseDTO findByNameResponse(String name);

    // Devuelve DTO Response del modelo IA
    IAResponseDTO findByModelResponse(String model);

    /*
     * ====================================
     * Desarrollo logica
     * ====================================
     */
    // Crear IA
    IAResponseDTO createIA(IARequestDTO iaRequestDTO);

    // Editar IA
    IAResponseDTO updateIA(Long id, IARequestDTO iaRequestDTO);

}
