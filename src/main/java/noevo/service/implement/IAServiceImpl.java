package noevo.service.implement;

//Java imports
import java.util.List;
import java.util.Optional;

//Org imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Noevo imports
import noevo.model.entity.IA;
import noevo.repository.IARepository;
import noevo.service.interfaces.IAService;
import noevo.model.dto.ia.IARequestDTO;
import noevo.model.dto.ia.IAResponseDTO;
import noevo.enums.OpcionesIdiomas;

@Service
public class IAServiceImpl implements IAService {

    @Autowired
    private IARepository iaRepository;

    // Basico
    @Override
    public List<IA> findAll() {
        return iaRepository.findAll();
    }

    @Override
    public Optional<IA> findById(Long id) {
        return iaRepository.findById(id);
    }

    @Override
    public IA saved(IA ia) {
        return iaRepository.save(ia);
    }

    @Override
    public void deleteById(Long id) {
        iaRepository.deleteById(id);
    }

    /*
     * ====================================
     * Personalizado Inicio
     * ====================================
     */
    @Override
    public Optional<IA> findByName(String name) {
        return iaRepository.findByName(name);
    }

    @Override
    public Optional<IA> findByRol(String rol) {
        return iaRepository.findByRol(rol);
    }

    // Buscar idioma de la IA
    @Override
    public Optional<IA> findByLanguage(OpcionesIdiomas language) {
        return iaRepository.findByLanguage(language);
    }

    @Override
    public boolean existsByName(String name) {
        return iaRepository.existsByName(name);
    }

    /*
     * ====================================
     * Personalizado Fin
     * ====================================
     */

    /*
     * ====================================
     * DTO Inicio
     * ====================================
     */
    // Devuelve DTO Response de todos las IA
    @Override
    public List<IAResponseDTO> findAllResponse() {
        List<IA> ia = findAll();

        return ia.stream()
                .map(ias -> IAResponseDTO.builder()
                        .id(ias.getId())
                        .name(ias.getName())
                        .rol(ias.getRol())
                        .language(ias.getLanguage())
                        .build())
                .toList();
    }

    // Devuelve DTO Response de una IA por id
    @Override
    public Optional<IAResponseDTO> findByIdResponse(Long id) {
        return findById(id)
                .map(ias -> IAResponseDTO.builder()
                        .id(ias.getId())
                        .name(ias.getName())
                        .rol(ias.getRol())
                        .language(ias.getLanguage())
                        .build());
    }

    // Devuelve DTO Response del nombre IA
    @Override
    public IAResponseDTO findByNameResponse(String name) {
        IA ia = findByName(name)
                .orElseThrow(() -> new RuntimeException("IA no encontrada"));

        return IAResponseDTO.builder()
                .id(ia.getId())
                .name(ia.getName())
                .rol(ia.getRol())
                .language(ia.getLanguage())
                .build();
    }

    // Devuelve DTO Response del rol IA
    @Override
    public IAResponseDTO findByRolResponse(String rol) {
        IA ia = findByRol(rol)
                .orElseThrow(() -> new RuntimeException("IA no encontrada"));

        return IAResponseDTO.builder()
                .id(ia.getId())
                .name(ia.getName())
                .rol(ia.getRol())
                .language(ia.getLanguage())
                .build();
    }

    // Devuelve DTO Response del idioma de la IA
    public IAResponseDTO findByLanguageResponse(OpcionesIdiomas language) {
        IA ia = findByLanguage(language)
                .orElseThrow(() -> new RuntimeException("IA no encontrada"));

        return IAResponseDTO.builder()
                .id(ia.getId())
                .name(ia.getName())
                .rol(ia.getRol())
                .language(ia.getLanguage())
                .build();
    }

    /*
     * ====================================
     * DTO Fin
     * ====================================
     */

    /*
     * ====================================
     * Desarrollo Logica Inicio
     * ====================================
     */
    // Crear IA
    @Override
    public IAResponseDTO createIA(IARequestDTO iaRequestDTO) {
        if (existsByName(iaRequestDTO.getName())) {
            throw new RuntimeException("Ya existe una IA con el nombre: " + iaRequestDTO.getName());
        }
        IA ia = IA.builder()
                .name(iaRequestDTO.getName())
                .rol(iaRequestDTO.getRol())
                .language(iaRequestDTO.getLanguage())
                .build();

        IA savedIA = saved(ia);
        return IAResponseDTO.builder()
                .id(savedIA.getId())
                .name(savedIA.getName())
                .rol(savedIA.getRol())
                .language(savedIA.getLanguage())
                .build();
    }

    // Editar IA
    @Override
    public IAResponseDTO updateIA(Long id, IARequestDTO iaRequestDTO) {
        IA ia = findById(id)
                .orElseThrow(() -> new RuntimeException("IA no encontrada"));

        ia.setName(iaRequestDTO.getName());
        ia.setRol(iaRequestDTO.getRol());
        ia.setLanguage(iaRequestDTO.getLanguage());

        IA updatedIA = iaRepository.save(ia);
        return IAResponseDTO.builder()
                .id(updatedIA.getId())
                .name(updatedIA.getName())
                .rol(updatedIA.getRol())
                .language(updatedIA.getLanguage())
                .build();
    }

    /*
     * ====================================
     * Desarrollo Logica Fin
     * ====================================
     */
}
