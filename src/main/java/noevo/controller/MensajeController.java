package noevo.controller;

//Java imports
import java.util.List;

//Org imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Jakarta imports
import jakarta.servlet.http.HttpServletRequest;
//Noevo imports
import noevo.service.implement.MensajeServiceImpl;
import noevo.model.dto.mensaje.MensajeRequestDTO;
import noevo.model.dto.mensaje.MensajeResponseDTO;

@RestController
@RequestMapping("/backend/api/mensaje")
public class MensajeController {

    @Autowired
    private MensajeServiceImpl mensajeServiceImpl;

    // Mostrar mensaje
    @GetMapping("/showMessage/ia/{iaId}/conversacion/{conversacionId}")
    public List<MensajeResponseDTO> showMessages(
            @PathVariable Long iaId,
            @PathVariable Long conversacionId,
            HttpServletRequest servletRequest) {
        Long usuarioId = (Long) servletRequest.getAttribute("usuarioId");

        return mensajeServiceImpl.showMessageConversation(conversacionId, usuarioId, iaId);
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
