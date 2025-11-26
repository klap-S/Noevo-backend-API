package noevo.service.interfaces;

//Noevo imports
import noevo.model.dto.mensaje.MensajeResponseDTO;

public interface IAGPTService {

    // Se envia el mensaje de usuario y se obtiene la respuesta de la IA
    MensajeResponseDTO messageAndResponse(
            Long usuarioId,
            Long iaId,
            Long conversacionId,
            String messageUsuario);

    // El mensaje de metodo anterior se envia a GPT y se obtiene la respuesta
    String sendMessageGPT(String message);

}
