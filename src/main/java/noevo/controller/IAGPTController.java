package noevo.controller;

//Jakarta imports
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

//Org imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//Noevo imports
import noevo.service.implement.IAGPTServiceImpl;
import noevo.model.dto.mensaje.MensajeRequestDTO;
import noevo.model.dto.mensaje.MensajeResponseDTO;

@RestController
@RequestMapping("/backend/api/gpt")
public class IAGPTController {

    @Autowired
    IAGPTServiceImpl iagptServiceImpl;

    // Enviar mensaje del usuario y obtener respuesta de la IA
    @PostMapping("/chat/ia/{iaId}")
    public MensajeResponseDTO messageAndResponse(
            @PathVariable Long iaId,
            @RequestParam(required = false) Long conversacionId,
            @Valid @RequestBody MensajeRequestDTO mensajeRequestDTO,
            HttpServletRequest servletRequest) {

        Long usuarioId = (Long) servletRequest.getAttribute("usuarioId");

        return iagptServiceImpl.messageAndResponse(
                usuarioId,
                iaId,
                conversacionId,
                mensajeRequestDTO.getContentText());
    }
}
