package noevo.controller;

//Java imports
import java.util.List;

//Org imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Lombok imports
import lombok.Builder;

//Noevo imports
import noevo.model.dto.usuario.UsuarioCreateRequestDTO;
import noevo.model.dto.usuario.UsuarioCreateResponseDTO;
import noevo.model.dto.usuario.UsuarioResponseDTO;
import noevo.model.dto.usuario.UsuarioUpdateRequestDTO;
import noevo.model.dto.usuario.UsuarioUpdateResponseDTO;
import noevo.service.implement.UsuarioServiceImpl;

@RestController
@RequestMapping("/backend/api/usuario")
@Builder
public class UsuarioController {

        @Autowired
        private UsuarioServiceImpl usuarioServiceImpl;

        // Obtener todos los usuarios
        @GetMapping
        public List<UsuarioResponseDTO> getAll() {
                return usuarioServiceImpl.findAllResponse();
        }

        // Obtener usuario Id
        @GetMapping("/{id}")
        public UsuarioResponseDTO getById(@PathVariable Long id) {
                return usuarioServiceImpl.findByIdResponse(id)
                                .orElseThrow(() -> new RuntimeException("Usuario no econtrado"));
        }

        // Crear un usuario
        @PostMapping("/createUser")
        public UsuarioCreateResponseDTO create(@RequestBody UsuarioCreateRequestDTO createDTO) {
                return usuarioServiceImpl.createUser(createDTO);
        }

        // Crear usario invitado
        @PostMapping("/create/invitado")
        public UsuarioResponseDTO createInvitado() {
                return usuarioServiceImpl.createUserInvitado();
        }

        // Actualizar usuario
        @PutMapping("/updateUser/{id}")
        public UsuarioUpdateResponseDTO updateUser(@PathVariable Long id,
                        @RequestBody UsuarioUpdateRequestDTO updateRequestDTO) {
                return usuarioServiceImpl.updateUser(id, updateRequestDTO);
        }

        // Eliminar usuario
        @DeleteMapping("/deleteUser/{id}")
        public void delete(@PathVariable Long id) {
                usuarioServiceImpl.deleteById(id);
        }

}
