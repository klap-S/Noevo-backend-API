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

    // Personalizados
    // Devuelve DTO response todoS
    @Override
    public List<IAResponseDTO> findAllResponse() {
        List<IA> ia = findAll();

        return ia.stream()
                .map(ias -> IAResponseDTO.builder()
                        .id(ias.getId())
                        .name(ias.getName())
                        .model(ias.getModel())
                        .language(ias.getLanguage())
                        .build())
                .toList();
    }

    // Devuelve DTO response por id
    @Override
    public Optional<IAResponseDTO> findByIdResponse(Long id) {
        return findById(id)
                .map(ias -> IAResponseDTO.builder()
                        .id(ias.getId())
                        .name(ias.getName())
                        .model(ias.getModel())
                        .language(ias.getLanguage())
                        .build());
    }

    // Devuelve DTO de nombre
    @Override
    public IAResponseDTO findByNameResponse(String name) {
        IA ia = findByName(name)
                .orElseThrow(() -> new RuntimeException("IA no encontrada"));

        return IAResponseDTO.builder()
                .id(ia.getId())
                .name(ia.getName())
                .model(ia.getModel())
                .language(ia.getLanguage())
                .build();
    }

    // Devuelve DTO de modelos
    @Override
    public IAResponseDTO findByModelResponse(String model) {
        IA ia = findByModel(model)
                .orElseThrow(() -> new RuntimeException("IA no encontrada"));

        return IAResponseDTO.builder()
                .id(ia.getId())
                .name(ia.getName())
                .model(ia.getModel())
                .language(ia.getLanguage())
                .build();
    }

    @Override
    public Optional<IA> findByName(String name) {
        return iaRepository.findByName(name);
    }

    @Override
    public Optional<IA> findByModel(String model) {
        return iaRepository.findByModel(model);
    }

    @Override
    public boolean existsByName(String name) {
        return iaRepository.existsByName(name);
    }

    // Crear IA
    @Override
    public IAResponseDTO createIA(IARequestDTO iaRequestDTO) {
        if (existsByName(iaRequestDTO.getName())) {
            throw new RuntimeException("Ya existe una IA con el nombre: " + iaRequestDTO.getName());
        }
        IA ia = IA.builder()
                .name(iaRequestDTO.getName())
                .model(iaRequestDTO.getModel())
                .language(iaRequestDTO.getLanguage())
                .build();

        IA savedIA = saved(ia);
        return IAResponseDTO.builder()
                .id(savedIA.getId())
                .name(savedIA.getName())
                .model(savedIA.getModel())
                .language(savedIA.getLanguage())
                .build();
    }

    // Editar IA
    @Override
    public IAResponseDTO updateIA(Long id, IARequestDTO iaRequestDTO) {
        IA ia = findById(id)
                .orElseThrow(() -> new RuntimeException("IA no encontrada"));

        ia.setName(iaRequestDTO.getName());
        ia.setModel(iaRequestDTO.getModel());
        ia.setLanguage(iaRequestDTO.getLanguage());

        IA updatedIA = iaRepository.save(ia);
        return IAResponseDTO.builder()
                .id(updatedIA.getId())
                .name(updatedIA.getName())
                .model(updatedIA.getModel())
                .language(updatedIA.getLanguage())
                .build();
    }

}
