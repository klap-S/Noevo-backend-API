package noevo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import noevo.model.entity.IA;
import noevo.service.interfaces.IAService;
import noevo.model.dto.ia.IARequestDTO;
import noevo.model.dto.ia.IAResponseDTO;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ia")
public class IAController {

        @Autowired
        private IAService iaService;

        // Obtener toda la ia
        @GetMapping
        public List<IAResponseDTO> getAll() {
                return iaService.findAll()
                                .stream()
                                .map(ia -> IAResponseDTO.builder()
                                                .id(ia.getId())
                                                .nombre(ia.getNombre())
                                                .modelo(ia.getModelo())
                                                .idioma(ia.getIdioma())
                                                .build())
                                .collect(Collectors.toList());
        }

        // Obtener ia Id
        @GetMapping("/{id}")
        public IAResponseDTO getById(@PathVariable Long id) {
                return iaService.findById(id)
                                .map(ia -> IAResponseDTO.builder()
                                                .id(ia.getId())
                                                .nombre(ia.getNombre())
                                                .modelo(ia.getModelo())
                                                .idioma(ia.getIdioma())
                                                .build())
                                .orElseThrow(() -> new RuntimeException("No encontrado"));
        }

        // Crear una IA
        @PostMapping("/create")
        public IAResponseDTO create(@RequestBody @Valid IARequestDTO request) {
                IA ia = new IA();
                ia.setNombre(request.getNombre());
                ia.setModelo(request.getModelo());
                ia.setIdioma(request.getIdioma());

                IA saved = iaService.save(ia);

                return IAResponseDTO.builder()
                                .id(ia.getId())
                                .nombre(saved.getNombre())
                                .modelo(saved.getModelo())
                                .idioma(saved.getIdioma())
                                .build();
        }

        // Actualizar ia
        @PutMapping("/update/{id}")
        public IAResponseDTO update(@PathVariable Long id, @RequestBody @Valid IARequestDTO request) {
                IA ia = iaService.findById(id)
                                .orElseThrow(() -> new RuntimeException("IA no encontrada"));

                ia.setNombre(request.getNombre());
                ia.setModelo(request.getModelo());
                ia.setIdioma(request.getIdioma());

                IA updated = iaService.save(ia);

                return IAResponseDTO.builder()
                                .id(updated.getId())
                                .nombre(updated.getNombre())
                                .modelo(updated.getModelo())
                                .idioma(updated.getIdioma())
                                .build();
        }

        // Eliminar ia
        @DeleteMapping("/delete/{id}")
        public void delete(@PathVariable Long id) {
                iaService.deleteById(id);
        }

}
