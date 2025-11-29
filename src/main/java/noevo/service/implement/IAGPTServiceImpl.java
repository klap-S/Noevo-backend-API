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
import noevo.model.dto.conversacion.ConversacionRequestDTO;
import noevo.model.dto.conversacion.ConversacionResponseDTO;
import noevo.model.dto.mensaje.MensajeRequestDTO;
import noevo.model.dto.mensaje.MensajeResponseDTO;
import noevo.model.entity.IA;

@Service
public class IAGPTServiceImpl implements IAGPTService {

        @Autowired
        private MensajeServiceImpl mensajeServiceImpl;
        @Autowired
        private ConversacionServiceImpl conversacionServiceImpl;
        @Autowired
        private IAServiceImpl iaServiceImpl;

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

        // Se envia el mensaje de usuario y se obtiene la respuesta de la IA, ademas se
        // guarda en la conversacion y si no existe se crea la conversacion
        @Override
        public MensajeResponseDTO messageAndResponse(
                        Long usuarioId,
                        Long iaId,
                        Long conversacionId,
                        String messageUsuario) {

                Long currentConversacionId = conversacionId;

                // Crea la conversacion si no existe por parte de la IA
                if (currentConversacionId == null) {

                        String promptTitle = "Genera un titulo muy corto con un maximo de 5 palabras para esta conversacion: \""
                                        + messageUsuario + "\"";
                        String promptContext = "Genera un contexto muy corto con un máximo 5 palabras para esta conversacion: \""
                                        + messageUsuario + "\"";

                        String title = sendMessageGPT(promptTitle);
                        String context = sendMessageGPT(promptContext);

                        ConversacionRequestDTO conversacionRequestDTO = new ConversacionRequestDTO();
                        conversacionRequestDTO.setTitle(title);
                        conversacionRequestDTO.setContext(context);

                        ConversacionResponseDTO conversacionResponseDTO = conversacionServiceImpl.createConversacion(
                                        usuarioId, iaId, conversacionRequestDTO);
                        currentConversacionId = conversacionResponseDTO.getId();
                }

                IA ia = iaServiceImpl.findById(iaId)
                                .orElseThrow(() -> new RuntimeException("IA no encontrada"));
                ia.getName();
                ia.getModel();
                ia.getLanguage();

                // Mensaje de usuario
                MensajeRequestDTO mensajeRequestDTO = new MensajeRequestDTO();
                mensajeRequestDTO.setContentText(messageUsuario);

                mensajeServiceImpl.createMessage(
                                usuarioId,
                                iaId,
                                currentConversacionId,
                                OpcionesTipoMensajes.TEXTO,
                                OpcionesRemitente.USUARIO,
                                OpcionesRemitente.IA,
                                mensajeRequestDTO);
                String promtMessageUsuario = "Eres una IA llamada " + ia.getName() + " con este rol:" + ia.getModel()
                                + " y las respuestas deben ser obligatorio en " + ia.getLanguage()
                                + ". Esta es la pregunta del usuario: " + messageUsuario;
                String responseIA = sendMessageGPT(promtMessageUsuario);

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
