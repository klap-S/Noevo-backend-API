package noevo.controller;

//Java imports
import java.util.List;

//Jakarta imports
import jakarta.validation.Valid;

//Org imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Noevo imports
import noevo.model.dto.conversacion.ConversacionRequestDTO;
import noevo.model.dto.conversacion.ConversacionResponseDTO;
import noevo.service.implement.ConversacionServiceImpl;

@RestController
@RequestMapping("/backend/api/conversacion")
public class ConversacionController {

        @Autowired
        private ConversacionServiceImpl conversacionServiceImpl;

        // Obtener todas las conversaciones que existen
        @GetMapping
        public List<ConversacionResponseDTO> getAll() {
                return conversacionServiceImpl.findAllResponse();
        }

        // Obtener conversacion Id
        @GetMapping("/{id}")
        public ConversacionResponseDTO getById(@PathVariable Long id) {
                return conversacionServiceImpl.findByIdResponse(id)
                                .orElseThrow(() -> new RuntimeException("Usuario no econtrado"));
        }

        // Editar conversacion
        @PutMapping("/updateConversacion/usuario/{usuarioId}/ia/{iaId}/conversacion/{conversacionId}")
        public ConversacionResponseDTO updateConversacion(
                        @PathVariable Long usuarioId,
                        @PathVariable Long iaId,
                        @PathVariable Long conversacionId,
                        @Valid @RequestBody ConversacionRequestDTO conversacionRequestDTO) {

                return conversacionServiceImpl.updateConversacion(conversacionId, usuarioId, iaId,
                                conversacionRequestDTO);
        }

        // Eliminar conversacion
        @DeleteMapping("/deleteConversacion/{id}")
        public void delete(@PathVariable Long id) {
                conversacionServiceImpl.deleteById(id);
        }

}
