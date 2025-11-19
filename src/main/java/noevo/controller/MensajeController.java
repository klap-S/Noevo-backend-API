package noevo.controller;

//Java imports
import java.util.List;

//Org imports
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

//Noevo imports
import noevo.service.implement.MensajeServiceImpl;
import noevo.enums.OpcionesTipoMensajes;
import noevo.model.dto.mensaje.MensajeRequestDTO;
import noevo.model.dto.mensaje.MensajeResponseDTO;

@RestController
@RequestMapping("/backend/api/mensaje")
public class MensajeController {

    @Autowired
    private MensajeServiceImpl mensajeServiceImpl;

    // Mostrar mensaje
    @GetMapping("/showMessage/usuario/{usuarioId}/ia/{iaId}/conversacion/{conversacionId}")
    public List<MensajeResponseDTO> showMessages(
            @PathVariable Long usuarioId,
            @PathVariable Long iaId,
            @PathVariable Long conversacionId) {

        return mensajeServiceImpl.showMessageConversation(conversacionId, usuarioId, iaId);
    }

    // Crear mensaje
    @PostMapping("/createMessage/usuario/{usuarioId}/ia/{iaId}")
    public MensajeResponseDTO createMessage(
            @PathVariable Long usuarioId,
            @PathVariable Long iaId,
            @RequestParam(value = "conversacionId", required = false) Long conversacionId,
            @RequestBody MensajeRequestDTO messageRequestDTO) {

        return mensajeServiceImpl.createMessage(
                usuarioId,
                iaId,
                conversacionId,
                OpcionesTipoMensajes.TEXTO,
                messageRequestDTO);
    }

    // Editar mensaje existente
    @PutMapping("/editMessage/usuario/{usuarioId}/ia/{iaId}/conversacion/{conversacionId}/mensaje/{mensajeId}")
    public MensajeResponseDTO editMessage(
            @PathVariable Long usuarioId,
            @PathVariable Long iaId,
            @PathVariable Long conversacionId,
            @PathVariable Long mensajeId,
            @RequestBody MensajeRequestDTO editRequest) {

        return mensajeServiceImpl.editMessageConversation(
                mensajeId,
                conversacionId,
                usuarioId,
                iaId,
                editRequest.getContentText());
    }

    // Eliminar mensaje
    @DeleteMapping("/deleteMessage/{id}")
    public void delete(@PathVariable Long id) {
        mensajeServiceImpl.deleteById(id);
    }

}
