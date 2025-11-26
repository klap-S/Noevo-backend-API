package noevo.controller;

//Java imports
import java.util.Map;

//Org imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import noevo.model.dto.mensaje.MensajeRequestDTO;
import noevo.model.dto.mensaje.MensajeResponseDTO;
//Noevo imports
import noevo.service.implement.IAGPTServiceImpl;

@RestController
@RequestMapping("/backend/api/gpt")
public class IAGPTController {

    @Autowired
    IAGPTServiceImpl iagptServiceImpl;

    @PostMapping("/chat/usuario/{usuarioId}/ia/{iaId}")
    public MensajeResponseDTO messageAndResponse(
            @PathVariable Long usuarioId,
            @PathVariable Long iaId,
            @RequestParam(required = false) Long conversacionId,
            @Valid @RequestBody MensajeRequestDTO mensajeRequestDTO) {

        return iagptServiceImpl.messageAndResponse(
                usuarioId,
                iaId,
                conversacionId,
                mensajeRequestDTO.getContentText());
    }
}
