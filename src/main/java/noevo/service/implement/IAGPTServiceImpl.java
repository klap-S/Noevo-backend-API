package noevo.service.implement;

//Java imports
import java.util.Map;
import java.util.List;

//Org imports
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

//Noevo imports
import noevo.service.interfaces.IAGPTService;
import noevo.enums.OpcionesRemitente;
import noevo.enums.OpcionesTipoMensajes;
import noevo.model.dto.mensaje.MensajeRequestDTO;
import noevo.model.dto.mensaje.MensajeResponseDTO;

@Service
public class IAGPTServiceImpl implements IAGPTService {

        @Autowired
        private MensajeServiceImpl mensajeServiceImpl;

        private final WebClient webClient;
        private final String model;

        // Constructor super necesario para inicializar WebClient con la clave API y
        // modelo desde application.properties
        public IAGPTServiceImpl(@Value("${openai.api.key}") String apiKey,
                        @Value("${openai.model}") String model) {
                this.webClient = WebClient.builder()
                                .baseUrl("https://api.openai.com/v1")
                                .defaultHeader("Authorization", "Bearer " + apiKey)
                                .build();
                this.model = model;
        }

        // Se envia el mensaje de usuario y se obtiene la respuesta de la IA
        @Override
        public MensajeResponseDTO messageAndResponse(
                        Long usuarioId,
                        Long iaId,
                        Long conversacionId,
                        String messageUsuario) {

                // Mensaje de usuario
                MensajeRequestDTO mensajeRequestDTO = new MensajeRequestDTO();
                mensajeRequestDTO.setContentText(messageUsuario);

                MensajeResponseDTO mensajeResponseDTOUsuario = mensajeServiceImpl.createMessage(
                                usuarioId,
                                iaId,
                                conversacionId,
                                OpcionesTipoMensajes.TEXTO,
                                OpcionesRemitente.USUARIO,
                                OpcionesRemitente.IA,
                                mensajeRequestDTO);
                Long currentConversacionId = mensajeResponseDTOUsuario.getConversacionId();
                String responseIA = sendMessageGPT(messageUsuario);

                // Mensaje de IA
                MensajeRequestDTO mensajeRequestDTOIA = new MensajeRequestDTO();
                mensajeRequestDTOIA.setContentText(responseIA);

                return mensajeServiceImpl.createMessage(
                                usuarioId,
                                iaId,
                                currentConversacionId,
                                OpcionesTipoMensajes.TEXTO,
                                OpcionesRemitente.IA,
                                OpcionesRemitente.USUARIO,
                                mensajeRequestDTOIA);
        }

        // El mensaje de metodo anterior se envia a GPT y se obtiene la respuesta
        @Override
        public String sendMessageGPT(String message) {
                Map<String, Object> request = Map.of(
                                "model", model,
                                "messages", new Object[] {
                                                Map.of("role", "user", "content", message)
                                });

                Map response = webClient.post()
                                .uri("/chat/completions")
                                .bodyValue(request)
                                .retrieve()
                                .bodyToMono(Map.class)
                                .block();

                if (response != null) {
                        var choices = (List<Map<String, Object>>) response.get("choices");
                        if (!choices.isEmpty()) {
                                var msg = (Map<String, Object>) choices.get(0).get("message");
                                return msg.get("content").toString();
                        }
                }
                return "Sin respuesta";
        }
}
