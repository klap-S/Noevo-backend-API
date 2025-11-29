package noevo.controller;

//Java imports
import java.util.List;

//Jakerta imports
import jakarta.validation.Valid;

//Org imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Noevo imports
import noevo.service.implement.IAServiceImpl;
import noevo.enums.OpcionesIdiomas;
import noevo.model.dto.ia.IARequestDTO;
import noevo.model.dto.ia.IAResponseDTO;

@RestController
@RequestMapping("/backend/api/ia")
public class IAController {

        @Autowired
        private IAServiceImpl iaServiceImpl;

        // Obtener toda la ia
        @GetMapping
        public List<IAResponseDTO> getAll() {
                return iaServiceImpl.findAllResponse();
        }

        // Obtener ia Id
        @GetMapping("/{id}")
        public IAResponseDTO getById(@PathVariable Long id) {
                return iaServiceImpl.findByIdResponse(id)
                                .orElseThrow(() -> new RuntimeException("Ia no encontrada"));
        }

        // Crear una IA
        @PostMapping("/createIA")
        public IAResponseDTO create(@RequestBody @Valid IARequestDTO iaRequestDTO) {
                return iaServiceImpl.createIA(iaRequestDTO);
        }

        // Actualizar IA
        @PutMapping("/updateIA/{id}")
        public IAResponseDTO updateIA(@PathVariable Long id, @RequestBody @Valid IARequestDTO iarequestDTO) {
                return iaServiceImpl.updateIA(id, iarequestDTO);
        }

        // Eliminar ia
        @DeleteMapping("/deleteIA/{id}")
        public void delete(@PathVariable Long id) {
                iaServiceImpl.deleteById(id);
        }

        // Buscar por nombre
        @GetMapping("/searchName/{name}")
        public IAResponseDTO getByName(@PathVariable String name) {
                return iaServiceImpl.findByNameResponse(name);
        }

        // Buscar por rol de la IA
        @GetMapping("/searchRol/{rol}")
        public IAResponseDTO getByRol(@PathVariable String rol) {
                return iaServiceImpl.findByRolResponse(rol);
        }

        // Buscar por idioma de la IA
        @GetMapping("/searchLanguage/{language}")
        public IAResponseDTO getByLanguage(@PathVariable OpcionesIdiomas language) {
                return iaServiceImpl.findByLanguageResponse(language);
        }

}
