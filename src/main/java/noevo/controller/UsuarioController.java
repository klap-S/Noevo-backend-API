package noevo.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import noevo.enums.RolUsuario;
import noevo.model.dto.usuario.PasswordResetDTO;
import noevo.model.dto.usuario.PasswordUpdateDTO;
import noevo.model.dto.usuario.UsuarioCreateRequestDTO;
import noevo.model.dto.usuario.UsuarioLoginRequestDTO;
import noevo.model.dto.usuario.UsuarioResponseDTO;
import noevo.model.dto.usuario.UsuarioUpdateRequestDTO;
import noevo.model.entity.Usuario;
import noevo.service.interfaces.UsuarioService;
import lombok.Builder;
import java.util.UUID;

@RestController
@RequestMapping("/usuario")
@Builder
public class UsuarioController {

        @Autowired
        private UsuarioService usuarioService;

        // Obtener todos los usuarios
        @GetMapping
        public List<UsuarioResponseDTO> getAll() {
                return usuarioService.findAll()
                                .stream()
                                .map(u -> UsuarioResponseDTO.builder()
                                                .id(u.getId())
                                                .nombre(u.getNombre())
                                                .email(u.getEmail())
                                                .idioma(u.getIdioma())
                                                .rol(u.getRol())
                                                .fechaRegistro(u.getFechaRegistro())
                                                .build())
                                .collect(Collectors.toList());
        }

        // Obtener usuario Id
        @GetMapping("/{id}")
        public UsuarioResponseDTO getById(@PathVariable Long id) {
                return usuarioService.findById(id)
                                .map(u -> UsuarioResponseDTO.builder()
                                                .id(u.getId())
                                                .nombre(u.getNombre())
                                                .email(u.getEmail())
                                                .idioma(u.getIdioma())
                                                .rol(u.getRol())
                                                .fechaRegistro(u.getFechaRegistro())
                                                .build())
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        }

        // Crear un usuario
        @PostMapping("/create")
        public UsuarioResponseDTO create(@RequestBody @Valid UsuarioCreateRequestDTO createDTO) {
                Usuario usuario = new Usuario();
                usuario.setNombre(createDTO.getNombre());
                if (usuarioService.existsByEmail(createDTO.getEmail())) {
                        throw new RuntimeException("El email ya esta registrado");
                } else {
                        usuario.setEmail(createDTO.getEmail());
                }
                usuario.setPassword(createDTO.getPassword());
                usuario.setIdioma(createDTO.getIdioma());
                usuario.setRol(RolUsuario.NORMAL);

                Usuario saved = usuarioService.save(usuario);
                return UsuarioResponseDTO.builder()
                                .id(saved.getId())
                                .nombre(saved.getNombre())
                                .email(saved.getEmail())
                                .idioma(saved.getIdioma())
                                .rol(saved.getRol())
                                .fechaRegistro(saved.getFechaRegistro())
                                .build();
        }

        // Crear usario invitado
        @PostMapping("/create/invitado")
        public UsuarioResponseDTO createInvitado() {
                Usuario usuario = new Usuario();
                usuario.setNombre("Invitado");

                int numeroAleatorio = (int) (Math.random() * 90 + 10);
                usuario.setEmail("invitado" + numeroAleatorio + "@noevo.com");

                usuario.setPassword("");
                usuario.setIdioma("");
                usuario.setRol(RolUsuario.INVITADO);

                Usuario saved = usuarioService.save(usuario);
                return UsuarioResponseDTO.builder()
                                .id(saved.getId())
                                .nombre(saved.getNombre())
                                .email(saved.getEmail())
                                .idioma(saved.getIdioma())
                                .rol(saved.getRol())
                                .fechaRegistro(saved.getFechaRegistro())
                                .build();
        }

        // Actualizar usuario
        @PutMapping("/update/{id}")
        public UsuarioResponseDTO updateUsuario(@PathVariable Long id,
                        @RequestBody @Valid UsuarioUpdateRequestDTO updateDTO) {
                Usuario usuario = usuarioService.findById(id)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                usuario.setNombre(updateDTO.getNombre());
                usuario.setEmail(updateDTO.getEmail());
                usuario.setIdioma(updateDTO.getIdioma());

                Usuario update = usuarioService.save(usuario);
                return UsuarioResponseDTO.builder()
                                .id(update.getId())
                                .nombre(update.getNombre())
                                .email(update.getEmail())
                                .idioma(update.getIdioma())
                                .rol(update.getRol())
                                .fechaRegistro(update.getFechaRegistro())
                                .build();
        }

        // Eliminar usuario
        @DeleteMapping("/{id}")
        public void delete(@PathVariable Long id) {
                usuarioService.deleteById(id);
        }

        // Login
        @PostMapping("/login")
        public UsuarioResponseDTO login(@RequestBody @Valid UsuarioLoginRequestDTO loginDTO) {
                Usuario usuario = usuarioService.findByEmail(loginDTO.getEmail())
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                if (!usuario.getPassword().equals(loginDTO.getPassword())) {
                        throw new RuntimeException("Contraseña incorrecta");
                }

                if (usuario.getRol() == RolUsuario.ADMIN) {
                        System.out.println("Inicio de sesión como administrador: " + usuario.getNombre());
                } else {
                        System.out.println("Iniciando sesion: " + usuario.getNombre());
                }

                usuario.setUltimoAcceso(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

                Usuario save = usuarioService.save(usuario);
                return UsuarioResponseDTO.builder()
                                .id(save.getId())
                                .nombre(save.getNombre())
                                .email(save.getEmail())
                                .idioma(save.getIdioma())
                                .rol(save.getRol())
                                .fechaRegistro(save.getFechaRegistro())
                                .build();
        }

        // Actualizar contraseña de usuario
        @PutMapping("/updatepassword/{id}")
        public UsuarioResponseDTO updatePassword(@PathVariable Long id,
                        @RequestBody @Valid PasswordUpdateDTO passwordUpdateDTO) {
                Usuario usuario = usuarioService.findById(id)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                if (!usuario.getPassword().equals(passwordUpdateDTO.getOldPassword())) {
                        throw new RuntimeException("La contraseña actual es incorrecta");
                }

                if (!passwordUpdateDTO.getNewPassword().equals(passwordUpdateDTO.getConfirmPassword())) {
                        throw new RuntimeException("Deben coincidir");
                }

                usuario.setPassword(passwordUpdateDTO.getConfirmPassword());
                Usuario update = usuarioService.save(usuario);
                return UsuarioResponseDTO.builder()
                                .id(update.getId())
                                .nombre(update.getNombre())
                                .email(update.getEmail())
                                .idioma(update.getIdioma())
                                .rol(update.getRol())
                                .fechaRegistro(update.getFechaRegistro())
                                .build();
        }

        // Resetear contraseña por si se olvida
        @PutMapping("/resetpassword/{id}")
        public UsuarioResponseDTO resetPassword(@PathVariable Long id,
                        @RequestBody @Valid PasswordResetDTO passwordResetDTO) {
                Usuario usuario = usuarioService.findById(id)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                if (!passwordResetDTO.getPassword().equals(passwordResetDTO.getConfirmPassword())) {
                        throw new RuntimeException("Las contraseñas no coinciden");
                }

                usuario.setPassword(passwordResetDTO.getPassword());
                Usuario update = usuarioService.save(usuario);
                return UsuarioResponseDTO.builder()
                                .id(update.getId())
                                .nombre(update.getNombre())
                                .email(update.getEmail())
                                .idioma(update.getIdioma())
                                .rol(update.getRol())
                                .fechaRegistro(update.getFechaRegistro())
                                .build();
        }

}
