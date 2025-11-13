package noevo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import noevo.model.dto.conversacion.ConversacionRequestDTO;
import noevo.model.dto.conversacion.ConversacionResponseDTO;
import noevo.model.dto.mensaje.MensajeResponseDTO;
import noevo.model.entity.Conversacion;
import noevo.model.entity.IA;
import noevo.model.entity.Usuario;
import noevo.service.interfaces.ConversacionService;
import noevo.service.interfaces.IAService;
import noevo.service.interfaces.UsuarioService;

@RestController
@RequestMapping("/conversacion")
public class ConversacionController {

        @Autowired
        private ConversacionService conversacionService;
        @Autowired
        private UsuarioService usuarioService;
        @Autowired
        private IAService iaService;

        // Obtener toda la conversacion
        @GetMapping
        public List<ConversacionResponseDTO> getAll() {
                return conversacionService.findAll().stream()
                                .map(c -> ConversacionResponseDTO.builder()
                                                .titulo(c.getTitulo())
                                                .contexto(c.getContexto())
                                                .iaId(c.getIa().getId())
                                                .usuarioId(c.getUsuario().getId())
                                                .fechaInicio(c.getFechaInicio())
                                                .build())
                                .collect(Collectors.toList());
        }

        // Obtener conversacion Id
        @GetMapping("/mensajeConversacion/{id}")
        public List<MensajeResponseDTO> getMensajes(@PathVariable Long id) {
                Conversacion conversacion = conversacionService.findById(id)
                                .orElseThrow(() -> new RuntimeException("Conversación no encontrada"));

                return conversacion.getMensajes()
                                .stream()
                                .map(m -> MensajeResponseDTO.builder()
                                                .contenidoTexto(m.getContenidoTexto())
                                                .tipo(m.getTipo())
                                                .fecha(m.getFecha())
                                                .build())
                                .collect(Collectors.toList());
        }

        // Crear una conversacion
        @PostMapping
        public ConversacionResponseDTO create(@RequestBody @Valid ConversacionRequestDTO dto) {
                IA ia = iaService.findById(dto.getIaId())
                                .orElseThrow(() -> new RuntimeException("IA no encontrada"));
                Usuario usuario = usuarioService.findById(dto.getUsuarioId())
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                Conversacion conversacion = new Conversacion();
                conversacion.setTitulo(dto.getTitulo());
                conversacion.setContexto(dto.getContexto());
                conversacion.setIa(ia);
                conversacion.setUsuario(usuario);

                Conversacion saved = conversacionService.save(conversacion);

                return ConversacionResponseDTO.builder()
                                .titulo(saved.getTitulo())
                                .contexto(saved.getContexto())
                                .iaId(saved.getIa().getId())
                                .usuarioId(saved.getUsuario().getId())
                                .fechaInicio(saved.getFechaInicio())
                                .build();
        }

        // Editar conversacion
        @PutMapping("/editarConversacion/{id}")
        public ConversacionResponseDTO updateConversacion(@PathVariable Long id,
                        @RequestBody ConversacionRequestDTO updateConversacionDTO) {
                Conversacion conversacion = conversacionService.findById(id)
                                .orElseThrow(() -> new RuntimeException("Conversación no encontrada"));

                if (updateConversacionDTO.getTitulo() != null && !updateConversacionDTO.getTitulo().isBlank()) {
                        conversacion.setTitulo(updateConversacionDTO.getTitulo());
                }
                if (updateConversacionDTO.getContexto() != null && !updateConversacionDTO.getContexto().isBlank()) {
                        conversacion.setContexto(updateConversacionDTO.getContexto());
                }

                Conversacion actualizada = conversacionService.save(conversacion);
                return ConversacionResponseDTO.builder()
                                .titulo(actualizada.getTitulo())
                                .contexto(actualizada.getContexto())
                                .iaId(actualizada.getIa().getId())
                                .fechaInicio(actualizada.getFechaInicio())
                                .build();
        }

        // Eliminar conversacion
        @DeleteMapping("/eliminarConversacion/{id}")
        public void delete(@PathVariable Long id) {
                conversacionService.deleteById(id);
        }

}
