package noevo.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import noevo.model.entity.Conversacion;
import noevo.model.entity.IA;
import noevo.model.entity.Mensaje;
import noevo.model.entity.Usuario;
import noevo.repository.ConversacionRepository;
import noevo.repository.IARepository;
import noevo.repository.UsuarioRepository;
import noevo.service.interfaces.MensajesService;
import noevo.enums.*;
import noevo.model.dto.mensaje.MensajeRequestDTO;
import noevo.model.dto.mensaje.MensajeResponseDTO;
import noevo.repository.MensajeRepository;
import java.util.Optional;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/mensajes")
public class MensajeController {

        @Autowired
        private MensajesService mensajesService;

        @Autowired
        private MensajeRepository mensajeRepository;
        @Autowired
        private ConversacionRepository conversacionRepository;
        @Autowired
        private UsuarioRepository usuarioRepository;
        @Autowired
        private IARepository iaRepository;

        // Crear mensaje Usuario
        @PostMapping("/enviarMensajeUsuario")
        public MensajeResponseDTO enviarMensaje(@RequestParam Long usuarioId, @RequestParam Long iaId,
                        OpcionesTipoMensajes tipoMensaje, @RequestBody MensajeRequestDTO enviarMensajeDTO) {

                Usuario usuario = usuarioRepository.findById(usuarioId)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                IA ia = iaRepository.findById(iaId)
                                .orElseThrow(() -> new RuntimeException("IA no encontrada"));

                Optional<Conversacion> conversacionOptional = conversacionRepository
                                .findFirstMessageByUsuarioAndIa(usuarioId, iaId);

                Conversacion conversacion;

                if (conversacionOptional.isPresent()) {
                        conversacion = conversacionOptional.get();
                } else {
                        conversacion = new Conversacion();
                        conversacion.setUsuario(usuario);
                        conversacion.setIa(ia);
                        conversacion.setTitulo("Conversacion_" + (int) (Math.random() * 10000));
                        conversacion.setContexto("Contexto_" + (int) (Math.random() * 10000));
                        conversacion.setFechaInicio(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                        conversacion = conversacionRepository.save(conversacion);
                }

                Mensaje mensaje = new Mensaje();
                mensaje.setConversacion(conversacion);
                mensaje.setUsuario(usuario);
                mensaje.setIa(ia);
                mensaje.setEmisor(OpcionesRemitente.USUARIO);
                mensaje.setRemitente(OpcionesRemitente.IA);
                mensaje.setTipo(tipoMensaje);
                mensaje.setContenidoTexto(enviarMensajeDTO.getContenidoTexto());
                mensaje.setFecha(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                mensaje.setOrden(conversacion.getMensajes().size() + 1);

                mensajeRepository.save(mensaje);
                return MensajeResponseDTO.builder()
                                .contenidoTexto(mensaje.getContenidoTexto())
                                .build();
        }

        // Crear mensaje IA
        @PostMapping("/enviarMensajeIA")
        public MensajeResponseDTO enviarMensaje() {
                return null;
        }

        // Editar mensaje solo el Usuario
        @PutMapping("/editarMensaje/{mensajeId}")
        public MensajeResponseDTO editarMensaje(@PathVariable Long mensajeId,
                        @RequestBody MensajeRequestDTO mensajeEditDTO) {
                Mensaje mensaje = mensajeRepository.findById(mensajeId)
                                .orElseThrow(() -> new RuntimeException("Mensaje no encontrado"));

                mensaje.setContenidoTexto(mensajeEditDTO.getContenidoTexto());
                Mensaje save = mensajeRepository.save(mensaje);
                return MensajeResponseDTO.builder()
                                .contenidoTexto(save.getContenidoTexto())
                                .build();
        }

        // Eliminar mensaje
        @DeleteMapping("/{id}")
        public void delete(@PathVariable Long id) {
                mensajesService.deleteById(id);
        }

}
